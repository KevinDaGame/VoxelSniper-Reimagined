package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Voxel_Disc_Brush
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
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.voxelDisc(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
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
