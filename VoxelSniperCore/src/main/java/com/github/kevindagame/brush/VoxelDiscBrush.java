package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#voxel-disc-brush">...</a>
 *
 * @author Voxel
 */
public class VoxelDiscBrush extends PerformerBrush {

    /**
     *
     */
    public VoxelDiscBrush() {
        this.setName("Voxel Disc");
    }

    private void voxelDisc(final SnipeData v) {
        this.positions = Shapes.voxelDisc(this.getTargetBlock().getLocation(), v.getBrushSize());
    }

    @Override
    protected final void doArrow(final SnipeData v) {
        this.voxelDisc(v);
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        this.voxelDisc(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.voxeldisc";
    }
}
