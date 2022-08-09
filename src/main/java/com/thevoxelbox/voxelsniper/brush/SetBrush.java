package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Set_Brush
 *
 * @author Voxel
 */
public class SetBrush extends PerformerBrush {

    private static final int SELECTION_SIZE_MAX = 5000000;
    private IBlock block = null;

    /**
     *
     */
    public SetBrush() {
        this.setName("Set");
    }

    /**
     *  NOTE: TRUE when first point was selected, or second point is in different world
     * @param bl block
     * @param v  data
     * @return TRUE when first point was selected, or second point is in different world
     */
    private boolean set(final IBlock bl, final SnipeData v) {
        if (this.block == null) {
            this.block = bl;
            return true;
        } else {
            if (!this.block.getWorld().getName().equals(bl.getWorld().getName())) {
                v.sendMessage(Messages.SELECTED_POINTS_DIFFERENT_WORLD);
                this.block = bl;
                return true;
            }
            final int lowX = Math.min(this.block.getX(), bl.getX());
            final int lowY = Math.min(this.block.getY(), bl.getY());
            final int lowZ = Math.min(this.block.getZ(), bl.getZ());
            final int highX = Math.max(this.block.getX(), bl.getX());
            final int highY = Math.max(this.block.getY(), bl.getY());
            final int highZ = Math.max(this.block.getZ(), bl.getZ());

            if (Math.abs(highX - lowX) * Math.abs(highZ - lowZ) * Math.abs(highY - lowY) > SELECTION_SIZE_MAX) {
                v.sendMessage(Messages.SELECTION_SIZE_LIMIT);
            } else {
                for (int y = lowY; y <= highY; y++) {
                    for (int x = lowX; x <= highX; x++) {
                        for (int z = lowZ; z <= highZ; z++) {
                            this.currentPerformer.perform(this.clampY(x, y, z));
                        }
                    }
                }
            }

            this.block = null;
            return false;
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (this.set(this.getTargetBlock(), v)) {
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
        } else {
            v.owner().storeUndo(this.currentPerformer.getUndo());
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.set(this.getLastBlock(), v)) {
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
        } else {
            v.owner().storeUndo(this.currentPerformer.getUndo());
        }
    }

    @Override
    public final void info(final VoxelMessage vm) {
        this.block = null;
        vm.brushName(this.getName());
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.set";
    }
}
