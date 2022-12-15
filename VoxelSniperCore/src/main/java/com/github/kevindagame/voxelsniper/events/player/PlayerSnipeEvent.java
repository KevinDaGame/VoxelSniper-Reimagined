package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.Cancellable;
import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import java.util.List;
import java.util.function.Consumer;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import org.jetbrains.annotations.NotNull;

public class PlayerSnipeEvent extends PlayerEvent<PlayerSnipeEvent>implements Cancellable {

    private static final HandlerList<PlayerSnipeEvent> handlers = new HandlerList<>();
    private final IBrush brush;
    private final List<VoxelLocation> positions;
    private EventResult status;
    public PlayerSnipeEvent(final IPlayer p, final IBrush brush, List<VoxelLocation> positions) {
        super(p);
        this.brush = brush;
        this.status = EventResult.DEFAULT;
        this.positions = positions;
    }

    public IBrush getBrush() {
        return brush;
    }

    public List<VoxelLocation> getPositions() {
        return positions;
    }

    public EventResult getStatus() {
        return status;
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
