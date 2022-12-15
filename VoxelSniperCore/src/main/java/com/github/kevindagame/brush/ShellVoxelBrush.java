package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * THIS BRUSH SHOULD NOT USE PERFORMERS. <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#shell-voxel-brush">...</a>
 *
 * @author Voxel
 */
public class ShellVoxelBrush extends AbstractBrush {

    /**
     *
     */
    public ShellVoxelBrush() {
        this.setName("Shell Voxel");
    }

    private void vShell(final SnipeData v, IBlock targetBlock) {
        final int brushSize = v.getBrushSize();
        final int brushSizeSquared = 2 * brushSize;
        final VoxelMaterial[][][] oldMaterials = new VoxelMaterial[2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1]; // Array that holds the original materials plus a  buffer
        final VoxelMaterial[][][] newMaterials = new VoxelMaterial[2 * brushSize + 1][2 * brushSize + 1][2 * brushSize + 1]; // Array that holds the hollowed materials

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
        for (int x = 0; x <= brushSizeSquared; x++) {
            for (int y = 0; y <= brushSizeSquared; y++) {
                System.arraycopy(oldMaterials[x + 1][y + 1], 1, newMaterials[x][y], 0, brushSizeSquared + 1);
            }
        }
        int temp;

        // Hollow Brush Area
        for (int x = 0; x <= brushSizeSquared; x++) {
            for (int z = 0; z <= brushSizeSquared; z++) {
                for (int y = 0; y <= brushSizeSquared; y++) {
                    temp = 0;

                    if (oldMaterials[x + 1 + 1][z + 1][y + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1 - 1][z + 1][y + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1][z + 1 + 1][y + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1][z + 1 - 1][y + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1][z + 1][y + 1 + 1] == v.getReplaceMaterial()) {
                        temp++;
                    }
                    if (oldMaterials[x + 1][z + 1][y + 1 - 1] == v.getReplaceMaterial()) {
                        temp++;
                    }

                    if (temp == 0) {
                        newMaterials[x][z][y] = v.getVoxelMaterial();
                    }
                }
            }
        }

        // Make the changes
        final Undo undo = new Undo();

        for (int x = brushSizeSquared; x >= 0; x--) {
            for (int y = 0; y <= brushSizeSquared; y++) {
                for (int z = brushSizeSquared; z >= 0; z--) {
                    setBlockMaterialAt(blockPositionX - brushSize + x, blockPositionY - brushSize + y, blockPositionZ - brushSize + z, newMaterials[x][y][z], undo);
                }
            }
        }
        v.owner().storeUndo(undo);

        v.sendMessage(Messages.SHELL_BRUSH_COMPLETE);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.vShell(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.vShell(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        vm.replace();
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.shellvoxel";
    }
}
