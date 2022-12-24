/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.util.brushOperation.BrushOperation;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Voxel
 */
public abstract class BasePerformer implements Predicate<IBlock> {

    public String name = "Performer";
    protected Undo h;
    protected IWorld w;

    public abstract void info(VoxelMessage vm);

    public abstract void init(SnipeData v);

    public void setUndo() {
        h = new Undo();
    }

    @Override
    public abstract boolean test(IBlock b);

    protected abstract BlockOperation perform(IBlock b);

    public List<BrushOperation> perform(List<BaseLocation> positions) {
        return positions.stream().map(BaseLocation::getBlock).filter(this).map(this::perform).collect(Collectors.toList());
    }

    public Undo getAndClearUndo() {
        Undo temp = h;
        h = null;
        return temp;
    }

    public boolean isUsingReplaceMaterial() {
        return false;
    }

}
