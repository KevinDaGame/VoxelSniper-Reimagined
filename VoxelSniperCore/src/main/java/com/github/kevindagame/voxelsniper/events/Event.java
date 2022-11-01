package com.github.kevindagame.voxelsniper.events;

public abstract class Event {
    private final boolean async;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public Event() {
        this(false);
    }

    /**
     * This constructor is used to explicitly declare an event as synchronous
     * or asynchronous.
     *
     * @param isAsync true indicates the event will fire asynchronously, false
     *     by default from default constructor
     */
    public Event(boolean isAsync) {
        this.async = isAsync;
    }

    public abstract <T extends Event> HandlerList<T> getHandlers();
    
    public final void callEvent() {
    	getHandlers().callEvent(this);
    }
}
