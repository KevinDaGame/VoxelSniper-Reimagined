package com.github.kevindagame.brush.MultiBlock;

import com.github.kevindagame.brush.AbstractBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.BlockWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Inherit this class for brushes that may change multiple block types.
 * For example a brush that randomly changes blocks to sand and dirt.
 * Instead of changing the blocks, add a blockWrapper to the operations list.
 */
public abstract class MultiBlockBrush extends AbstractBrush {
    protected final List<BlockWrapper> operations = new ArrayList<>();

    @Override
    protected boolean actPerform(SnipeData v) {
        Undo undo = new Undo();
        for (var operation : operations) {
            if (positions.contains(operation.getLocation())) {
                var block = operation.getLocation().getBlock();
                undo.put(block);
                block.setMaterial(operation.getMaterial(), false);
            }
        }
        v.owner().storeUndo(undo);
        return true;
    }

    @Override
    protected final void arrow(SnipeData v) {
        operations.clear();
        this.doArrow(v);
        //add location of operations to positions so they can be used in event handling
        operations.forEach(location -> positions.add(location.getLocation()));
    }

    @Override
    protected final void powder(SnipeData v) {
        operations.clear();
        this.doPowder(v);
        //add location of operations to positions so they can be used in event handling
        operations.forEach(location -> positions.add(location.getLocation()));
    }

    protected abstract void doArrow(SnipeData v);

    protected abstract void doPowder(SnipeData v);
}
