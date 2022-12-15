package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#extrude-brush">...</a>
 *
 * @author psanker
 */
public class ExtrudeBrush extends AbstractBrush {

    private static final double SMOOTH_CIRCLE_VALUE = 0.5;
    private static final double VOXEL_CIRCLE_VALUE = 0.0;

    private boolean smoothCircle = false;

    /**
     *
     */
    public ExtrudeBrush() {
        this.setName("Extrude");
    }

    private void extrudeUpOrDown(final SnipeData v, boolean isUp) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);
        Undo undo = new Undo();

        for (int x = -brushSize; x <= brushSize; x++) {
            final double xSquared = Math.pow(x, 2);
            for (int z = -brushSize; z <= brushSize; z++) {
                if ((xSquared + Math.pow(z, 2)) <= brushSizeSquared) {
                    final int direction = (isUp ? 1 : -1);
                    for (int y = 0; y < Math.abs(v.getVoxelHeight()); y++) {
                        final int tempY = y * direction;
                        undo = this.perform(
                                this.clampY(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + tempY, this.getTargetBlock().getZ() + z),
                                this.clampY(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + tempY + direction, this.getTargetBlock().getZ() + z),
                                v, undo);
                    }
                }
            }
        }

        v.owner().storeUndo(undo);
    }

    private void extrudeNorthOrSouth(final SnipeData v, boolean isSouth) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);
        Undo undo = new Undo();

        for (int x = -brushSize; x <= brushSize; x++) {
            final double xSquared = Math.pow(x, 2);
            for (int y = -brushSize; y <= brushSize; y++) {
                if ((xSquared + Math.pow(y, 2)) <= brushSizeSquared) {
                    final int direction = (isSouth) ? 1 : -1;
                    for (int z = 0; z < Math.abs(v.getVoxelHeight()); z++) {
                        final int tempZ = z * direction;
                        undo = this.perform(
                                this.clampY(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + tempZ),
                                this.clampY(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + tempZ + direction),
                                v, undo);
                    }

                }
            }
        }

        v.owner().storeUndo(undo);
    }

    private void extrudeEastOrWest(final SnipeData v, boolean isEast) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);
        Undo undo = new Undo();

        for (int y = -brushSize; y <= brushSize; y++) {
            final double ySquared = Math.pow(y, 2);
            for (int z = -brushSize; z <= brushSize; z++) {
                if ((ySquared + Math.pow(z, 2)) <= brushSizeSquared) {
                    final int direction = (isEast) ? 1 : -1;
                    for (int x = 0; x < Math.abs(v.getVoxelHeight()); x++) {
                        final int tempX = x * direction;
                        undo = this.perform(
                                this.clampY(this.getTargetBlock().getX() + tempX, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z),
                                this.clampY(this.getTargetBlock().getX() + tempX + direction, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z),
                                v, undo);
                    }

                }
            }
        }
        v.owner().storeUndo(undo);
    }

    private Undo perform(final IBlock b1, final IBlock b2, final SnipeData v, final Undo undo) {
        IBlock clampedBlock1 = this.clampY(b1.getX(), b1.getY(), b1.getZ());
        IBlock clampedBlock2 = this.clampY(b2.getX(), b2.getY(), b2.getZ());
        if (v.getVoxelList().contains(clampedBlock1.getMaterial())) {
            undo.put(clampedBlock2);
            clampedBlock2.setBlockData(clampedBlock1.getBlockData());
        }

        return undo;
    }

    private void selectExtrudeMethod(final SnipeData v, final BlockFace blockFace, final boolean towardsUser) {
        if (blockFace == null || v.getVoxelHeight() == 0) {
            return;
        }
        boolean tempDirection = towardsUser;
        switch (blockFace) {
            case DOWN:
                tempDirection = !towardsUser;
            case UP:
                extrudeUpOrDown(v, tempDirection);
                break;
            case NORTH:
                tempDirection = !towardsUser;
            case SOUTH:
                extrudeNorthOrSouth(v, tempDirection);
                break;
            case WEST:
                tempDirection = !towardsUser;
            case EAST:
                extrudeEastOrWest(v, tempDirection);
                break;
            default:
                break;
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.selectExtrudeMethod(v, this.getTargetBlock().getFace(this.getLastBlock()), false);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.selectExtrudeMethod(v, this.getTargetBlock().getFace(this.getLastBlock()), true);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.height();
        vm.voxelList();

        if (this.smoothCircle) {
            vm.custom(Messages.BRUSH_SMOOTH_CIRCLE);
        }
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.EXTRUDE_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].startsWith("smooth")) {
            this.smoothCircle = !this.smoothCircle;
            v.sendMessage(Messages.BRUSH_SMOOTH_CIRCLE);
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("smooth"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.extrude";
    }
}
