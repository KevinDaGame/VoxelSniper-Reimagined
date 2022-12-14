package com.github.kevindagame.brush.Shell;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;

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
        this.positions = Shapes.ball(this.getTargetBlock().getLocation(), v.getBrushSize(), false);
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        final int brushSize = v.getBrushSize();
        var newMaterials = this.bShell(v);
        final Undo undo = new Undo();
        for (var position : this.positions) {
            var material = newMaterials[position.getBlockX() - this.getTargetBlock().getX() + brushSize][position.getBlockY() - this.getTargetBlock().getY() + brushSize][position.getBlockZ() - this.getTargetBlock().getZ() + brushSize];
            undo.put(position.getBlock());
            position.getBlock().setMaterial(material);
        }
        v.owner().storeUndo(undo);
        v.sendMessage(Messages.SHELL_BRUSH_COMPLETE);
        return true;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.shellball";
    }
}
