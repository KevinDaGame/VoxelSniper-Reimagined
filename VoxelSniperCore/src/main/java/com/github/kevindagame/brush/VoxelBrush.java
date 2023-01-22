package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#voxel-brush">...</a>
 *
 * @author Piotr
 */
public class VoxelBrush extends PerformerBrush {


    private void voxel(final SnipeData v) {
        this.positions = Shapes.voxel(this.getTargetBlock().getLocation(), v.getBrushSize());
    }

    @Override
    protected final void doArrow(final SnipeData v) {
        this.voxel(v);
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        this.voxel(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }
}
