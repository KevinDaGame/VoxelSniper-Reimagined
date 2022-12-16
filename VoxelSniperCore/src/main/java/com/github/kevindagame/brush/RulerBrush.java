package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeAction;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#ruler-brush">...</a>
 *
 * @author Gavjenks
 */
public class RulerBrush extends AbstractBrush {

    private final int xOff = 0;
    private final int yOff = 0;
    private final int zOff = 0;
    private boolean first = true;
    private VoxelVector coords = new VoxelVector();

    /**
     *
     */
    public RulerBrush() {
        this.setName("Ruler");
    }


    @Override
    protected boolean actPerform(SnipeData v) {
        switch (snipeAction) {
            case ARROW -> {
                if (positions.size() == 1) {
                        this.coords = positions.stream().toList().get(0).toVector();
                        v.sendMessage(Messages.FIRST_POINT_SELECTED);
                        this.first = !this.first;
                }
            }
            case GUNPOWDER -> {
                var target = positions.stream().toList().get(0);
                double x = target.getX() - this.coords.getX();
                double y = target.getY() - this.coords.getY();
                double z = target.getZ() - this.coords.getZ();
                final double distance = (double) (Math.round(target.toVector().subtract(this.coords).length() * 100) / 100);
                final double blockDistance = (double) (Math.round((Math.abs(Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z))) + 1) * 100) / 100);

                v.sendMessage(Messages.RULER_BRUSH_POWDER.replace("%x%", String.valueOf(x)).replace("%y%", String.valueOf(y)).replace("%z%", String.valueOf(z)).replace("%distance%", String.valueOf(distance)).replace("%blockDistance%", String.valueOf(blockDistance)));
            }
        }
        return true;
    }

    @Override
    protected final void arrow(final SnipeData v) {
        positions.add(getTargetBlock().getLocation());
    }


    @Override
    protected final void powder(final SnipeData v) {
        if (this.coords == null || this.coords.lengthSquared() == 0) {
            v.sendMessage(Messages.FIRST_COORDINATE_NOT_SET);
            return;
        }
        positions.add(getTargetBlock().getLocation());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.voxel();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.RULER_BRUSH_USAGE);
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.ruler";
    }
}
