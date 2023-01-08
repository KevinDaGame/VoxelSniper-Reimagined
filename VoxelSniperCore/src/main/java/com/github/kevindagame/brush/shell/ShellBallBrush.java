package com.github.kevindagame.brush.shell;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.brushOperation.BlockOperation;

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

    @Override
    protected final void shell(final SnipeData v) {
        var positions = Shapes.ball(this.getTargetBlock().getLocation(), v.getBrushSize(), false);
        var newMaterials = this.bShell(v);
        final int brushSize = v.getBrushSize();
        for (var position : positions) {
            var material = newMaterials[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockY() - this.getTargetBlock().getY() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
            addOperation(new BlockOperation(position, position.getBlock().getBlockData(), material.createBlockData()));
        }
        v.sendMessage(Messages.SHELL_BRUSH_COMPLETE);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.shellball";
    }
}
