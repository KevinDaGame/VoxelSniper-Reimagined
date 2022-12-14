package com.github.kevindagame.voxelsniper.events;

public interface CancellableEvent {
    /**
     * Gets the cancellation state of this event. The action tied to a cancelled event will not be executed, but the event will still pass along to plugins.
     *
     * @return true if this event is cancelled
     */
    public boolean isCancelled();

    /**
     * Sets the cancellation state of this event. The action tied to a cancelled event will not be executed, but the event will still pass along to plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    public void setCancelled(boolean cancel);
}
