package com.github.kevindagame.brush.Shell;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;

/**
 * THIS BRUSH SHOULD NOT USE PERFORMERS. <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#shell-voxel-brush">...</a>
 *
 * @author Voxel
 */
public class ShellVoxelBrush extends ShellBrushBase {

    /**
     *
     */
    public ShellVoxelBrush() {
        this.setName("Shell Voxel");
    }

    @Override
    protected void shell(SnipeData v) {
        this.positions = Shapes.voxel(getTargetBlock().getLocation(), v.getBrushSize());
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
        return "voxelsniper.brush.shellvoxel";
    }

}