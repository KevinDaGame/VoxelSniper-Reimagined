package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MikeMatrix
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#block-reset-brush-brush">...</a>
 */
public class BlockResetBrush extends AbstractBrush {

    private static List<VoxelMaterial> getDENIED_UPDATES() {
        return Arrays.asList(
                VoxelMaterial.getMaterial("acacia_sign"),
                VoxelMaterial.getMaterial("birch_sign"),
                VoxelMaterial.getMaterial("dark_oak_sign"),
                VoxelMaterial.getMaterial("jungle_sign"),
                VoxelMaterial.getMaterial("oak_sign"),
                VoxelMaterial.getMaterial("spruce_sign"),
                VoxelMaterial.getMaterial("acacia_wall_sign"),
                VoxelMaterial.getMaterial("birch_wall_sign"),
                VoxelMaterial.getMaterial("dark_oak_wall_sign"),
                VoxelMaterial.getMaterial("jungle_wall_sign"),
                VoxelMaterial.getMaterial("oak_wall_sign"),
                VoxelMaterial.getMaterial("spruce_wall_sign"),
                VoxelMaterial.getMaterial("chest"),
                VoxelMaterial.getMaterial("furnace"),
                VoxelMaterial.getMaterial("redstone_torch"),
                VoxelMaterial.getMaterial("redstone_wall_torch"),
                VoxelMaterial.getMaterial("redstone_wire"),
                VoxelMaterial.getMaterial("repeater"),
                VoxelMaterial.getMaterial("acacia_door"),
                VoxelMaterial.getMaterial("birch_door"),
                VoxelMaterial.getMaterial("dark_oak_door"),
                VoxelMaterial.getMaterial("jungle_door"),
                VoxelMaterial.getMaterial("oak_door"),
                VoxelMaterial.getMaterial("spruce_door"),
                VoxelMaterial.getMaterial("acacia_trapdoor"),
                VoxelMaterial.getMaterial("birch_trapdoor"),
                VoxelMaterial.getMaterial("dark_oak_trapdoor"),
                VoxelMaterial.getMaterial("jungle_trapdoor"),
                VoxelMaterial.getMaterial("oak_trapdoor"),
                VoxelMaterial.getMaterial("spruce_trapdoor"),
                VoxelMaterial.getMaterial("acacia_fence_gate"),
                VoxelMaterial.getMaterial("birch_fence_gate"),
                VoxelMaterial.getMaterial("dark_oak_fence_gate"),
                VoxelMaterial.getMaterial("jungle_fence_gate"),
                VoxelMaterial.getMaterial("oak_fence_gate")
        );
    }

    private void applyBrush(final SnipeData v) {
        addOperations(Shapes.voxel(this.getTargetBlock().getLocation(), v.getBrushSize()).stream().map(BaseLocation::getBlock)
                .filter(block -> BlockResetBrush.getDENIED_UPDATES().contains(block.getMaterial())).map(b -> new BlockOperation(b.getLocation(), b.getBlockData(), b.getBlockData().getMaterial().createBlockData())).collect(Collectors.toList()));
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
