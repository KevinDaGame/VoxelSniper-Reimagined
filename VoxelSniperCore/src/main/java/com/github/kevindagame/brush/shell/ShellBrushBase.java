package com.github.kevindagame.brush.shell;

import com.github.kevindagame.brush.AbstractBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;

/**
 * THIS BRUSH SHOULD NOT USE PERFORMERS. <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#shell-ball-brush">...</a>
 *
 * @author Voxel
 */
public abstract class ShellBrushBase extends AbstractBrush {


    /**
     * @param v
     */
    protected abstract void shell(SnipeData v);

    protected VoxelMaterialType[][][] bShell(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final int brushSizeDoubled = 2 * brushSize;
        final VoxelMaterialType[][][] oldMaterials = new VoxelMaterialType[2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1]; // Array that holds the original materials plus a buffer
        final VoxelMaterialType[][][] newMaterials = new VoxelMaterialType[brushSizeDoubled + 1][brushSizeDoubled + 1][brushSizeDoubled + 1]; // Array that holds the hollowed materials
        final var targetBlock = getTargetBlock();

        int blockPositionX = targetBlock.getX();
        int blockPositionY = targetBlock.getY();
        int blockPositionZ = targetBlock.getZ();

        // Log current materials into oldmats
        for (int x = 0; x <= 2 * (brushSize + 1); x++) {
            for (int y = 0; y <= 2 * (brushSize + 1); y++) {
                for (int z = 0; z <= 2 * (brushSize + 1); z++) {
                    oldMaterials[x][y][z] = this.getBlockMaterialAt(blockPositionX - brushSize - 1 + x, blockPositionY - brushSize - 1 + y, blockPositionZ - brushSize - 1 + z);
                }
            }
        }

        // Log current materials into newmats
        for (int x = 0; x <= brushSizeDoubled; x++) {
            for (int y = 0; y <= brushSizeDoubled; y++) {
                System.arraycopy(oldMaterials[x + 1][y + 1], 1, newMaterials[x][y], 0, brushSizeDoubled + 1);
            }
        }

        int temp;

        // Hollow Brush Area
        for (int x = 0; x <= brushSizeDoubled; x++) {
            for (int y = 0; y <= brushSizeDoubled; y++) {
                for (int z = 0; z <= brushSizeDoubled; z++) {
                    temp = 0;

                    if (oldMaterials[x + 1 + 1][y + 1][z + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1 - 1][y + 1][z + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1][y + 1 + 1][z + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1][y + 1 - 1][z + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1][y + 1][z + 1 + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1][y + 1][z + 1 - 1] == v.getReplaceMaterial()) {
                        temp++;
                    }

                    if (temp == 0) {
                        newMaterials[x][y][z] = v.getVoxelMaterial();
                    }
                }
            }
        }
        return newMaterials;
    }

    @Override
    protected void arrow(final SnipeData v) {
        this.shell(v);
    }

    @Override
    protected void powder(final SnipeData v) {
        this.shell(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        vm.replace();
    }
}
