package com.github.kevindagame.voxelsniper.events;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public class HandlerList<T extends Event> {
	// Using a TreeSet to force the handlers to be sorted
	private final Set<EventListener<T>> handlers = new TreeSet<>();
	public void registerListener(EventPriority priority, Consumer<T> handler) {
		this.handlers.add(new EventListener<>(priority, handler));
	}
	public void registerListener(Consumer<T> handler) {
		this.registerListener(EventPriority.NORMAL, handler);
	}
	public void callEvent(T ev) {
		for(Consumer<T> handler : handlers) {
			handler.accept(ev);
		}
	}

}
