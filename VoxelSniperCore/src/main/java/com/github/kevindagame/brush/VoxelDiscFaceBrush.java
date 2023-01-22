package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#voxel-disc-face-brush">...</a>
 *
 * @author Voxel
 */
public class VoxelDiscFaceBrush extends PerformerBrush {


    private void voxelDiscFace(final SnipeData v) {
        this.positions = Shapes.voxelDiscFace(this.getTargetBlock().getLocation(), v.getBrushSize(), this.getTargetBlock().getFace(this.getLastBlock()));
    }

    @Override
    protected final void doArrow(final SnipeData v) {
        voxelDiscFace(v);
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        voxelDiscFace(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }
}
