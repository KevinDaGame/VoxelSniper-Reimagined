package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Voxel_Disc_Face_Brush
 *
 * @author Voxel
 */
public class VoxelDiscFaceBrush extends PerformerBrush {

    /**
     *
     */
    public VoxelDiscFaceBrush() {
        this.setName("Voxel Disc Face");
    }

    private void voxelDiscFace(final SnipeData v) {
        this.positions = Shapes.voxelDiscFace(this.getTargetBlock().getLocation(), v.getBrushSize(), this.getTargetBlock().getFace(this.getLastBlock()));
    }

    @Override
    protected final void arrow(final SnipeData v) {
        voxelDiscFace(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        voxelDiscFace(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.voxeldiscface";
    }
}
