package com.github.kevindagame.brush.shell;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.brushOperation.BlockOperation;

/**
 * THIS BRUSH SHOULD NOT USE PERFORMERS. <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#shell-voxel-brush">...</a>
 *
 * @author Voxel
 */
public class ShellVoxelBrush extends ShellBrushBase {


    @Override
    protected void shell(SnipeData v) {
        var positions = Shapes.voxel(getTargetBlock().getLocation(), v.getBrushSize());
        final int brushSize = v.getBrushSize();
        var newMaterials = this.bShell(v);
        for (var position : positions) {
            var material = newMaterials[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockY() - this.getTargetBlock().getY() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
            addOperation(new BlockOperation(position, position.getBlock().getBlockData(), material.createBlockData()));
        }
        v.sendMessage(Messages.SHELL_BRUSH_COMPLETE);
    }

}
