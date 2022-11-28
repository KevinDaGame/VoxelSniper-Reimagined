package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

public class PlayerSnipeEvent extends PlayerEvent<PlayerSnipeEvent> {

    private static final HandlerList<PlayerSnipeEvent> handlers = new HandlerList<>();
    private final IBrush brush;
    private final boolean success;

    public PlayerSnipeEvent(IPlayer p, IBrush brush, boolean success) {
        super(p);
        this.brush = brush;
        this.success = success;
    }

    public IBrush getBrush() {
        return brush;
    }

    public boolean isSuccessful() {
        return success;
    }

    @NotNull
    @Override
    protected HandlerList<PlayerSnipeEvent> getHandlers() {
        return handlers;
    }
    public static void registerListener(Consumer<PlayerSnipeEvent> handler) {
        handlers.registerListener(handler);
    }

    public static void registerListener(EventPriority priority, Consumer<PlayerSnipeEvent> handler) {
        handlers.registerListener(priority, handler);
    }
}
