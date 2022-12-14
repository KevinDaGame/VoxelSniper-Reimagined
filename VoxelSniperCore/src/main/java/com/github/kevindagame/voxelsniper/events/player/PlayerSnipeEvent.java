package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.CancellableEvent;
import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import java.util.List;
import java.util.function.Consumer;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import org.jetbrains.annotations.NotNull;

public class PlayerSnipeEvent extends PlayerEvent<PlayerSnipeEvent>implements CancellableEvent {

    private static final HandlerList<PlayerSnipeEvent> handlers = new HandlerList<>();
    private final IBrush brush;
    private EventResult status;
    public PlayerSnipeEvent(IPlayer p, IBrush brush, List<VoxelLocation> positions) {
        super(p);
        this.brush = brush;
        this.status = EventResult.DEFAULT;
    }

    public IBrush getBrush() {
        return brush;
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

    @Override
    public boolean isCancelled() {
        return status == EventResult.DENY;
    }

    @Override
    public void setCancelled(boolean cancel) {
        status = cancel ? EventResult.DENY : EventResult.DEFAULT;
    }
}
