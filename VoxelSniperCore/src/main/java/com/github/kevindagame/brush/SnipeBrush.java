package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#snipe-brush">...</a>
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
    protected final void doArrow(final SnipeData v) {
        this.positions.add(this.getTargetBlock().getLocation());
    }

    @Override
    protected final void doPowder(final SnipeData v) {
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
