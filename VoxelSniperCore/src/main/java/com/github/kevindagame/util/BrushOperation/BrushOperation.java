package com.github.kevindagame.util.BrushOperation;

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
}
