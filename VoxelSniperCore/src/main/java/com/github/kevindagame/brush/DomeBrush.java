package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#dome-brush">...</a>
 *
 * @author Gavjenks
 * @author MikeMatrix
 */
public class DomeBrush extends PerformerBrush {

    /**
     * @param v
     */
    private void generateDome(final SnipeData v) {

        if (v.getVoxelHeight() == 0) {
            v.sendMessage(Messages.VOXEL_HEIGHT_MUST_NOT_BE_0);
            return;
        }

        this.positions = Shapes.dome(this.getTargetBlock().getLocation(), v.getBrushSize(), v.getVoxelHeight());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        vm.height();
    }

    @Override
    protected final void doArrow(final SnipeData v) {
        this.generateDome(v);
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        this.generateDome(v);
    }
}
