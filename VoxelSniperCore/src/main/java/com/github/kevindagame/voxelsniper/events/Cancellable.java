package com.github.kevindagame.voxelsniper.events;

public interface Cancellable {
    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed by VoxelSniper, but will still pass to other {@link EventListener}s
     *
     * @return True if this event is cancelled by another {@link EventListener}
     */
    boolean isCancelled();

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed by VoxelSniper, but will still pass to other {@link EventListener}s.
     *
     * @param cancel True if you wish to cancel this event.
     * @apiNote Another {@link EventListener} might be able to un-cancel the event
     */
    void setCancelled(boolean cancel);
}
