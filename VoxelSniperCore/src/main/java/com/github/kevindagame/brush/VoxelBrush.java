package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#voxel-brush">...</a>
 *
 * @author Piotr
 */
public class VoxelBrush extends PerformerBrush {

    /**
     *
     */
    public VoxelBrush() {
        this.setName("Voxel");
    }

    private void voxel(final SnipeData v) {
        for (int z = v.getBrushSize(); z >= -v.getBrushSize(); z--) {
            for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
                for (int y = v.getBrushSize(); y >= -v.getBrushSize(); y--) {
                    this.currentPerformer.perform(this.clampY(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + z, this.getTargetBlock().getZ() + y));
                }
            }
        }
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.voxel(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.voxel(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.voxel";
    }
}
