package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * THIS BRUSH SHOULD NOT USE PERFORMERS. http://www.voxelwiki.com/minecraft/Voxelsniper#Shell_Brushes
 *
 * @author Voxel
 */
public class ShellBallBrush extends AbstractBrush {

    /**
     *
     */
    public ShellBallBrush() {
        this.setName("Shell Ball");
    }

    // parameters isn't an abstract method, gilt. You can just leave it out if there are none.
    private void bShell(final SnipeData v, IBlock targetBlock) {
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

        // Make the changes
        final Undo undo = new Undo();
        final double rSquared = Math.pow(brushSize + 0.5, 2);

        for (int x = brushSizeDoubled; x >= 0; x--) {
            final double xSquared = Math.pow(x - brushSize, 2);

            for (int y = 0; y <= 2 * brushSize; y++) {
                final double ySquared = Math.pow(y - brushSize, 2);

                for (int z = 2 * brushSize; z >= 0; z--) {
                    if (xSquared + ySquared + Math.pow(z - brushSize, 2) <= rSquared) {
                        setBlockMaterialAt(blockPositionX - brushSize + x, blockPositionY - brushSize + y, blockPositionZ - brushSize + z, newMaterials[x][y][z], undo);
                    }
                }
            }
        }
        v.owner().storeUndo(undo);

        // This is needed because most uses of this brush will not be sible to the sniper.
        v.sendMessage(Messages.SHELL_BRUSH_COMPLETE);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.bShell(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.bShell(v, this.getLastBlock());
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
        return "voxelsniper.brush.shellball";
    }
}
