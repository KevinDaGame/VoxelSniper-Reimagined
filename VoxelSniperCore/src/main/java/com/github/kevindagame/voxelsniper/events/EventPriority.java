package com.github.kevindagame.voxelsniper.events;

/**
 * Represents an {@link EventListener}'s priority in execution.
 * <p>
 * Listeners with lower priority are called first,
 * listeners with higher priority are called last.
 * <p>
 * Listeners are called in following order:
 * {@link #LOW} -> {@link #NORMAL} -> {@link #HIGH}
 */
public enum EventPriority {

    /**
     * Event call is of low importance and should be run first, to allow
     * other plugins to further customise the outcome
     */
    LOW,
    /**
     * Event call is neither important nor unimportant, and may be run
     * normally.<p>
     * This is also the default EventPriority for an {@link EventListener}.
     */
    NORMAL,
    /**
     * Event call is critical and must have the final say in what happens
     * to the event
     */
    HIGH;
}
