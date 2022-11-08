package com.github.kevindagame.voxelsniper.events;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

/**
 * Container to keep track of different settings for EventListeners
 * @param <T> The {@link Event} type to listen for
 */
public record EventListener<T extends Event>(EventPriority priority, Consumer<T> handler) implements Consumer<T>, Comparable<EventListener<?>> {

    @Override
    public void accept(T event) {
        this.handler.accept(event);
    }

    @Override
    public int compareTo(@NotNull EventListener o) {
        return this.priority.compareTo(o.priority);
    }

}
