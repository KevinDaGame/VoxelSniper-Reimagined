package com.github.kevindagame.brush;

import com.github.kevindagame.util.BrushOperation.BlockOperation;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author MikeMatrix
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#block-reset-brush-brush">...</a>
 */
public class BlockResetBrush extends AbstractBrush {

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
        addOperations(Shapes.voxel(this.getTargetBlock().getLocation(), v.getBrushSize()).stream().map(BaseLocation::getBlock)
                .filter(block -> BlockResetBrush.DENIED_UPDATES.contains(block.getMaterial())).map(b -> new BlockOperation(b.getLocation(), b.getBlockData(), b.getBlockData().getMaterial().createBlockData())).collect(Collectors.toList()));
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
