/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.world.IWorld;

/**
 * @author Voxel
 */
public abstract class vPerformer {

    public String name = "Performer";
    protected Undo h;
    protected IWorld w;

    public abstract void info(VoxelMessage vm);

    public abstract void init(com.github.kevindagame.snipe.SnipeData v);

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
