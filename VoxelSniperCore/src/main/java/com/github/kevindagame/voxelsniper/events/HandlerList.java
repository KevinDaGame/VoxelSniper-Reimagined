package com.github.kevindagame.voxelsniper.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Keeps track of all the eventListeners for a specific {@link Event}
 * @param <T> The {@link Event} type this HandlerList is for
 */
public class HandlerList<T extends Event> {
	private final List<EventListener<T>> handlers = new ArrayList<>();

	/**
	 * Registers an {@link EventListener} with a given {@link EventPriority}
	 *
	 * @param priority The {@link EventPriority} for this event
	 * @param handler  The handler to register
	 */
	public void registerListener(EventPriority priority, Consumer<T> handler) {
		this.handlers.add(new EventListener<>(priority, handler));
		Collections.sort(this.handlers);
	}

	/**
	 * Registers an {@link EventListener}
	 * @param handler The handler to register
	 */
	public void registerListener(Consumer<T> handler) {
		this.registerListener(EventPriority.MEDIUM, handler);
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
