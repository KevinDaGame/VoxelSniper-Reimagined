package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.voxelsniper.events.Cancellable;
import com.github.kevindagame.voxelsniper.location.BaseLocation;

/**
 * This class is the base class for all operations
 */
public abstract class BrushOperation implements Cancellable {

    private final BaseLocation location;
    private boolean cancelled;

    public BrushOperation(BaseLocation location) {
        this.location = location;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public BaseLocation getLocation() {
        return location;
    }

    /**
     * DO NOT USE THIS METHOD
     * Performs the operation.
     *
     * @param undo the undo object to add the operation to
     *
     * @return whether to reload the brush area
     */
    @Deprecated
    public abstract boolean perform(Undo undo);
}
