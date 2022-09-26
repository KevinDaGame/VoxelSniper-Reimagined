package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;

/**
 * @author MikeMatrix
 */
public class BlockResetBrush extends Brush {

    private static final ArrayList<VoxelMaterial> DENIED_UPDATES = new ArrayList<>();

    static {
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.ACACIA_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.BIRCH_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.DARK_OAK_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.JUNGLE_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.OAK_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.ACACIA_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.BIRCH_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.DARK_OAK_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.JUNGLE_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.OAK_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.CHEST);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.FURNACE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.REDSTONE_TORCH);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.REDSTONE_WALL_TORCH);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.REDSTONE_WIRE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.REPEATER);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.ACACIA_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.BIRCH_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.DARK_OAK_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.JUNGLE_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.OAK_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.IRON_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.ACACIA_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.BIRCH_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.DARK_OAK_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.JUNGLE_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterial.OAK_FENCE_GATE);
    }

    /**
     *
     */
    public BlockResetBrush() {
        this.setName("Block Reset Brush");
    }

    private void applyBrush(final SnipeData v) {
        for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++) {
            for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++) {
                for (int y = -v.getBrushSize(); y <= v.getBrushSize(); y++) {
                    final IBlock block = this.getWorld().getBlock(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                    if (BlockResetBrush.DENIED_UPDATES.contains(block.getMaterial())) {
                        continue;
                    }

                    // Resets the block state to initial state by creating a new BlockData with default values.
                    block.setBlockData(block.getBlockData().getMaterial().createBlockData(), true);

                    // Do we need this???
                    // block.setBlockData(MagicValues.getBlockDataFor(MagicValues.getIdFor(block.getMaterial()), oldData), true);
                }
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        applyBrush(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        applyBrush(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.blockreset";
    }
}