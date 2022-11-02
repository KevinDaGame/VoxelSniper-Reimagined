package com.github.kevindagame.voxelsniper.events;

/**
 * Abstract class for all events
 */
public abstract class Event {

    /**
     *
     * @return the {@link HandlerList} for this event
     * @param <T> The Event type this {@link HandlerList} is for
     */
    protected abstract <T extends Event> HandlerList<T> getHandlers();

    /**
     * Call the registered {@link EventListener}s for this event
     */
    public final void callEvent() {
    	getHandlers().callEvent(this);
    }
}
