package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * @author MikeMatrix
 */
public class BlockResetBrush extends Brush {

    private static final ArrayList<Material> DENIED_UPDATES = new ArrayList<>();

    static {
        BlockResetBrush.DENIED_UPDATES.add(Material.ACACIA_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.BIRCH_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.DARK_OAK_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.JUNGLE_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.OAK_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.ACACIA_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.BIRCH_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.DARK_OAK_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.JUNGLE_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.OAK_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.CHEST);
        BlockResetBrush.DENIED_UPDATES.add(Material.FURNACE);
        BlockResetBrush.DENIED_UPDATES.add(Material.REDSTONE_TORCH);
        BlockResetBrush.DENIED_UPDATES.add(Material.REDSTONE_WALL_TORCH);
        BlockResetBrush.DENIED_UPDATES.add(Material.REDSTONE_WIRE);
        BlockResetBrush.DENIED_UPDATES.add(Material.REPEATER);
        BlockResetBrush.DENIED_UPDATES.add(Material.ACACIA_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.BIRCH_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.DARK_OAK_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.JUNGLE_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.OAK_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.IRON_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.ACACIA_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(Material.BIRCH_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(Material.DARK_OAK_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(Material.JUNGLE_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(Material.OAK_FENCE_GATE);
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
                    final Block block = this.getWorld().getBlockAt(this.getTargetBlock().getX() + x, this.getTargetBlock().getY() + y, this.getTargetBlock().getZ() + z);
                    if (BlockResetBrush.DENIED_UPDATES.contains(block.getType())) {
                        continue;
                    }

                    // Resets the block state to initial state by creating a new BlockData with default values.
                    block.setBlockData(block.getBlockData().getMaterial().createBlockData(), true);

                    // Do we need this???
                    // block.setBlockData(MagicValues.getBlockDataFor(MagicValues.getIdFor(block.getType()), oldData), true);
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
