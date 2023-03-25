package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author MikeMatrix
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#block-reset-brush-brush">...</a>
 */
public class BlockResetBrush extends AbstractBrush {

    private static final ArrayList<VoxelMaterialType> DENIED_UPDATES = new ArrayList<>();

    static {
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.ACACIA_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.BIRCH_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.DARK_OAK_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.JUNGLE_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.OAK_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.ACACIA_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.BIRCH_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.DARK_OAK_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.JUNGLE_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.OAK_WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.CHEST);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.FURNACE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.REDSTONE_TORCH);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.REDSTONE_WALL_TORCH);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.REDSTONE_WIRE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.REPEATER);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.ACACIA_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.BIRCH_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.DARK_OAK_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.JUNGLE_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.OAK_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.IRON_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.ACACIA_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.BIRCH_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.DARK_OAK_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.JUNGLE_FENCE_GATE);
        BlockResetBrush.DENIED_UPDATES.add(VoxelMaterialType.OAK_FENCE_GATE);
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
}
