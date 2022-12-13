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
public class ShellBallBrush extends ShellBrushBase {

    /**
     *
     */
    public ShellBallBrush() {
        this.setName("Shell Ball");
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
    public String getPermissionNode() {
        return "voxelsniper.brush.shellball";
    }
}
