package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VoxelVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Three-Point_Circle_Brush
 *
 * @author Giltwist
 */
public class ThreePointCircleBrush extends PerformerBrush {

    private VoxelVector coordsOne;
    private VoxelVector coordsTwo;
    private VoxelVector coordsThree;
    private Tolerance tolerance = Tolerance.DEFAULT;

    /**
     * Default Constructor.
     */
    public ThreePointCircleBrush() {
        this.setName("3-Point Circle");
    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (this.coordsOne == null) {
            this.coordsOne = this.getTargetBlock().getLocation().toVector();
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
        } else if (this.coordsTwo == null) {
            this.coordsTwo = this.getTargetBlock().getLocation().toVector();
            v.sendMessage(Messages.SECOND_POINT_SELECTED);
        } else if (this.coordsThree == null) {
            this.coordsThree = this.getTargetBlock().getLocation().toVector();
            v.sendMessage(Messages.THIRD_POINT_SELECTED);
        } else {
            this.coordsOne = this.getTargetBlock().getLocation().toVector();
            this.coordsTwo = null;
            this.coordsThree = null;
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.coordsOne == null || this.coordsTwo == null || this.coordsThree == null) {
            return;
        }

        // Calculate triangle defining vectors
        final VoxelVector vectorOne = this.coordsTwo.clone();
        vectorOne.subtract(this.coordsOne);
        final VoxelVector vectorTwo = this.coordsThree.clone();
        vectorTwo.subtract(this.coordsOne);
        final VoxelVector vectorThree = this.coordsThree.clone();
        vectorThree.subtract(vectorTwo);

        // Redundant data check
        if (vectorOne.length() == 0 || vectorTwo.length() == 0 || vectorThree.length() == 0 || vectorOne.angle(vectorTwo) == 0 || vectorOne.angle(vectorThree) == 0 || vectorThree.angle(vectorTwo) == 0) {

            v.sendMessage(Messages.ERROR_INVALID_POINTS);
            this.coordsOne = null;
            this.coordsTwo = null;
            this.coordsThree = null;
            return;
        }

        // Calculate normal vector of the plane.
        final VoxelVector normalVector = vectorOne.clone();
        normalVector.crossProduct(vectorTwo);

        // Calculate constant term of the plane.
        final double planeConstant = normalVector.getX() * this.coordsOne.getX() + normalVector.getY() * this.coordsOne.getY() + normalVector.getZ() * this.coordsOne.getZ();

        final VoxelVector midpointOne = this.coordsOne.getMidpoint(this.coordsTwo);
        final VoxelVector midpointTwo = this.coordsOne.getMidpoint(this.coordsThree);

        // Find perpendicular vectors to two sides in the plane
        final VoxelVector perpendicularOne = normalVector.clone();
        perpendicularOne.crossProduct(vectorOne);
        final VoxelVector perpendicularTwo = normalVector.clone();
        perpendicularTwo.crossProduct(vectorTwo);

        // determine value of parametric variable at intersection of two perpendicular bisectors
        final VoxelVector tNumerator = midpointTwo.clone();
        tNumerator.subtract(midpointOne);
        tNumerator.crossProduct(perpendicularTwo);
        final VoxelVector tDenominator = perpendicularOne.clone();
        tDenominator.crossProduct(perpendicularTwo);
        final double t = tNumerator.length() / tDenominator.length();

        // Calculate Circumcenter and Brushcenter.
        final VoxelVector circumcenter = new VoxelVector();
        circumcenter.copy(perpendicularOne);
        circumcenter.multiply(t);
        circumcenter.add(midpointOne);

        final VoxelVector brushCenter = new VoxelVector(Math.round(circumcenter.getX()), Math.round(circumcenter.getY()), Math.round(circumcenter.getZ()));

        // Calculate radius of circumcircle and determine brushsize
        final double radius = circumcenter.distance(new VoxelVector(this.coordsOne.getX(), this.coordsOne.getY(), this.coordsOne.getZ()));
        final int brushSize = (int) Math.ceil(radius) + 1;

        for (int x = -brushSize; x <= brushSize; x++) {
            for (int y = -brushSize; y <= brushSize; y++) {
                for (int z = -brushSize; z <= brushSize; z++) {
                    // Calculate distance from center
                    final double tempDistance = Math.pow(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2), .5);

                    // gets corner-on blocks
                    final double cornerConstant = normalVector.getX() * (circumcenter.getX() + x) + normalVector.getY() * (circumcenter.getY() + y) + normalVector.getZ() * (circumcenter.getZ() + z);

                    // gets center-on blocks
                    final double centerConstant = normalVector.getX() * (circumcenter.getX() + x + .5) + normalVector.getY() * (circumcenter.getY() + y + .5) + normalVector.getZ() * (circumcenter.getZ() + z + .5);

                    // Check if point is within sphere and on plane (some tolerance given)
                    if (tempDistance <= radius && (Math.abs(cornerConstant - planeConstant) < this.tolerance.getValue() || Math.abs(centerConstant - planeConstant) < this.tolerance.getValue())) {
                        this.currentPerformer.perform(this.clampY(brushCenter.getBlockX() + x, brushCenter.getBlockY() + y, brushCenter.getBlockZ() + z));
                    }

                }
            }
        }

        v.sendMessage(Messages.TRI_POINT_CIRCLE_DONE);
        v.owner().storeUndo(this.currentPerformer.getUndo());

        // Reset Brush
        this.coordsOne = null;
        this.coordsTwo = null;
        this.coordsThree = null;

    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        switch (this.tolerance) {
            case ACCURATE:
                vm.custom(Messages.TRI_POINT_CIRCLE_MODE_ACCURATE);
                break;
            case DEFAULT:
                vm.custom(Messages.TRI_POINT_CIRCLE_MODE_DEFAULT);
                break;
            case SMOOTH:
                vm.custom(Messages.TRI_POINT_CIRCLE_MODE_SMOOTH);
                break;
            default:
                vm.custom(Messages.TRI_POINT_CIRCLE_MODE_UNKNOWN);
                break;
        }

    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.THREE_POINT_CIRCLE_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            this.tolerance = Tolerance.valueOf(params[0].toUpperCase());
            v.sendMessage(Messages.BRUSH_TOLERANCE_SET.replace("%tolerance%", this.tolerance.name().toLowerCase()));
        } catch (Exception e) {
            v.sendMessage(Messages.TOLERANCE_SETTING_DOES_NOT_EXIST.replace("%triggerHandle%", triggerHandle));
            sendPerformerMessage(triggerHandle, v);
        }
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();

        arguments.addAll(Arrays.stream(Tolerance.values()).map(e -> e.name()).collect(Collectors.toList()));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.threepointcircle";
    }

    /**
     * Enumeration on Tolerance values.
     *
     * @author MikeMatrix
     */
    private enum Tolerance {
        DEFAULT(1000), ACCURATE(10), SMOOTH(2000);
        private final int value;

        Tolerance(final int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
