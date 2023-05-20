package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeAction;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.Set;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#eraser-brush">...</a>
 *
 * @author Voxel
 */
public class EraserBrush extends AbstractBrush {
    private static final Set<VoxelMaterial> EXCLUSIVE_MATERIALS = Set.of(
            VoxelMaterial.getMaterial("air"),
            VoxelMaterial.getMaterial("cave_air"),
            VoxelMaterial.getMaterial("void_air"),
            VoxelMaterial.getMaterial("stone"),
            VoxelMaterial.getMaterial("grass_block"),
            VoxelMaterial.getMaterial("dirt"),
            VoxelMaterial.getMaterial("sand"),
            VoxelMaterial.getMaterial("gravel"),
            VoxelMaterial.getMaterial("sandstone"),
            VoxelMaterial.getMaterial("deepslate")
    );
    private static final Set<VoxelMaterial> EXCLUSIVE_LIQUIDS = Set.of(
            VoxelMaterial.WATER, VoxelMaterial.LAVA);


    private void doErase(final SnipeData v) {

        var positions = Shapes.voxel(this.getTargetBlock().getLocation(), v.getBrushSize());
        for (var pos : positions) {
            var currentBlock = pos.getBlock();
            if (EXCLUSIVE_MATERIALS.contains(currentBlock.getMaterial())
                    || (getSnipeAction() == SnipeAction.GUNPOWDER && EXCLUSIVE_LIQUIDS.contains(currentBlock.getMaterial()))) {
                continue;
            }
            addOperation(new BlockOperation(pos, currentBlock.getBlockData(), VoxelMaterial.AIR.createBlockData()));
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.doErase(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.doErase(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }
}
