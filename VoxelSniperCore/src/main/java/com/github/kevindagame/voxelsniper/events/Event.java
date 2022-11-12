package com.github.kevindagame.voxelsniper.events;

/**
 * Abstract class for all events
 * @param <T> The Event type this {@link HandlerList} is for
 */
public abstract class Event<T extends Event<?>> {

    /**
     *
     * @return the {@link HandlerList} for this event
     */
    protected abstract HandlerList<T> getHandlers();

    /**
     * Call the registered {@link EventListener}s for this event
     */
    public final T callEvent() {
    	return getHandlers().callEvent((T)this);
    }


}
