package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeAction;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.Set;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#eraser-brush">...</a>
 *
 * @author Voxel
 */
public class EraserBrush extends AbstractBrush {
    private static final Set<VoxelMaterial> EXCLUSIVE_MATERIALS = Set.of(
            VoxelMaterial.AIR, VoxelMaterial.CAVE_AIR, VoxelMaterial.VOID_AIR, VoxelMaterial.STONE, VoxelMaterial.GRASS_BLOCK, VoxelMaterial.DIRT, VoxelMaterial.SAND, VoxelMaterial.GRAVEL, VoxelMaterial.SANDSTONE);
    private static final Set<VoxelMaterial> EXCLUSIVE_LIQUIDS = Set.of(
            VoxelMaterial.WATER, VoxelMaterial.LAVA);

    static {
        try {
            // 1.17+
            EXCLUSIVE_MATERIALS.add(VoxelMaterial.DEEPSLATE);
        } catch (Throwable ignore) {
            // Don't add for older versions
        }
    }

    /**
     *
     */
    public EraserBrush() {
        this.setName("Eraser");
    }

    private void doErase(final SnipeData v) {

        positions = Shapes.voxel(this.getTargetBlock().getLocation(), v.getBrushSize());
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        Undo undo = new Undo();
        for(var pos: positions) {
            var currentBlock = pos.getBlock();
            if (EXCLUSIVE_MATERIALS.contains(currentBlock.getMaterial())
                    || (snipeAction == SnipeAction.GUNPOWDER && EXCLUSIVE_LIQUIDS.contains(currentBlock.getMaterial()))) {
                continue;
            }
            undo.put(currentBlock);
            currentBlock.setMaterial(VoxelMaterial.AIR);
        }
        v.owner().storeUndo(undo);
        return true;
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

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.eraser";
    }
}
