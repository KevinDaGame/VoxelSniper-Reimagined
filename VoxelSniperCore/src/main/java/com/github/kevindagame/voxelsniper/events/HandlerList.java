package com.github.kevindagame.voxelsniper.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HandlerList<T extends Event> {
	private final List<Consumer<T>> handlers = new ArrayList<>();
	
	public void registerListener(Consumer<T> handler) {
		handlers.add(handler);
	}
	
	public void callEvent(T ev) {
		for(Consumer<T> handler : handlers) {
			handler.accept(ev);
		}
	}

}
