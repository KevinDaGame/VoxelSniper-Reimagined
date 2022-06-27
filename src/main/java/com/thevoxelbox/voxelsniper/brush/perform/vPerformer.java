/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public abstract class vPerformer {

    public String name = "Performer";
    protected Undo h;
    protected World w;

    public abstract void info(VoxelMessage vm);

    public abstract void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v);

    public void setUndo() {
        h = new Undo();
    }

    public abstract void perform(IBlock b);

    public Undo getUndo() {
        Undo temp = h;
        h = null;
        return temp;
    }

    public boolean isUsingReplaceMaterial() {
        return false;
    }
}
