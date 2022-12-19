package com.github.kevindagame.util.BrushOperation;

/**
 * This class is the base class for all operations
 */
public class AbstractOperation implements BrushOperation {
    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
