package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;

/**
 *
 * @author Voxel
 */
public class SnipeBrush extends PerformerBrush {

    /**
     *
     */
    public SnipeBrush() {
        this.setName("Snipe");
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.positions.add(this.getTargetBlock().getLocation());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.positions.add(this.getTargetBlock().getLocation());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.snipe";
    }
}
