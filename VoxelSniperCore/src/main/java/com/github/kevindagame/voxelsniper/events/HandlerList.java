package com.github.kevindagame.voxelsniper.events;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * Keeps track of all the eventListeners for a specific {@link Event}
 * @param <T> The {@link Event} type this HandlerList is for
 */
public class HandlerList<T extends Event> {
	// Using a TreeSet to force the handlers to be sorted
	private final Set<EventListener<T>> handlers = new TreeSet<>();

	/**
	 * Registers an {@link EventListener} with a given {@link EventPriority}
	 * @param priority the {@link EventPriority} for this event
	 * @param handler the handler to register
	 */
	public void registerListener(EventPriority priority, Consumer<T> handler) {
		this.handlers.add(new EventListener<>(priority, handler));
	}

	/**
	 * Registers an {@link EventListener}
	 * @param handler the handler to register
	 */
	public void registerListener(Consumer<T> handler) {
		this.registerListener(EventPriority.NORMAL, handler);
	}

	/**
	 * Call the registered {@link EventListener}s for this event
	 */
	public void callEvent(T ev) {
		for(Consumer<T> handler : handlers) {
			handler.accept(ev);
		}
	}

}
