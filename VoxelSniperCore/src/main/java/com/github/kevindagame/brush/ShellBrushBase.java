package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Monofraps
 */
public abstract class ShellBrushBase extends AbstractBrush {


    /**
     * @param v
     */
    protected abstract void shell(SnipeData v);

    protected void bShell(final SnipeData v, IBlock targetBlock) {
        final int brushSize = v.getBrushSize();
        final int brushSizeDoubled = 2 * brushSize;
        final VoxelMaterial[][][] oldMaterials = new VoxelMaterial[2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1]; // Array that holds the original materials plus a buffer
        final VoxelMaterial[][][] newMaterials = new VoxelMaterial[brushSizeDoubled + 1][brushSizeDoubled + 1][brushSizeDoubled + 1]; // Array that holds the hollowed materials

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
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.shell(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
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
