package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import org.bukkit.block.Block;

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

    private void disc(final SnipeData v, Block targetBlock) {
        for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
            for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
                currentPerformer.perform(targetBlock.getRelative(x, 0, z));
            }
        }
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.disc(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.disc(v, this.getLastBlock());
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
