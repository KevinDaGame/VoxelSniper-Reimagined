package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.events.Cancellable;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;

/**
 * This class is the base class for all operations
 */
public class BrushOperation implements Cancellable {

    private final VoxelLocation location;
    private boolean cancelled;

    public BrushOperation(VoxelLocation location) {
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

    public VoxelLocation getLocation() {
        return location;
    }
}
