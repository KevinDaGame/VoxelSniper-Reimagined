package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
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

    private void doErase(final SnipeData v, final boolean keepWater) {
        final int brushSize = v.getBrushSize();
        final int brushSizeDoubled = 2 * brushSize;
        IWorld world = this.getTargetBlock().getWorld();
        final Undo undo = new Undo();

        for (int x = brushSizeDoubled; x >= 0; x--) {
            int currentX = this.getTargetBlock().getX() - brushSize + x;
            for (int y = 0; y <= brushSizeDoubled; y++) {
                int currentY = this.getTargetBlock().getY() - brushSize + y;
                for (int z = brushSizeDoubled; z >= 0; z--) {
                    int currentZ = this.getTargetBlock().getZ() - brushSize + z;
                    IBlock currentBlock = world.getBlock(currentX, currentY, currentZ);
                    if (EXCLUSIVE_MATERIALS.contains(currentBlock.getMaterial())
                            || (keepWater && EXCLUSIVE_LIQUIDS.contains(currentBlock.getMaterial()))) {
                        continue;
                    }
                    undo.put(currentBlock);
                    currentBlock.setMaterial(VoxelMaterial.AIR);
                }
            }
        }
        v.owner().storeUndo(undo);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.doErase(v, false);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.doErase(v, true);
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
