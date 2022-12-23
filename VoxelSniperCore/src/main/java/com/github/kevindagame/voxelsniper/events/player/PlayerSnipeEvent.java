package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.util.BrushOperation.BrushOperation;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.Cancellable;
import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.HandlerList;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class PlayerSnipeEvent extends PlayerEvent<PlayerSnipeEvent> implements Cancellable {

    private static final HandlerList<PlayerSnipeEvent> handlers = new HandlerList<>();
    private final IBrush brush;
    private final ImmutableList<BrushOperation> operations;
    private boolean isCustom;
    private EventResult status;

    public PlayerSnipeEvent(final IPlayer p, final IBrush brush, ImmutableList<BrushOperation> operations) {
        super(p);
        this.brush = brush;
        this.status = EventResult.DEFAULT;
        this.operations = operations;
        this.isCustom = false;
    }

    public PlayerSnipeEvent(final IPlayer p, final IBrush brush, ImmutableList<BrushOperation> operations, boolean isCustom) {
        this(p, brush, operations);
        this.isCustom = isCustom;
    }

    public static void registerListener(Consumer<PlayerSnipeEvent> handler) {
        handlers.registerListener(handler);
    }

    public static void registerListener(EventPriority priority, Consumer<PlayerSnipeEvent> handler) {
        handlers.registerListener(priority, handler);
    }

    public IBrush getBrush() {
        return brush;
    }

    public ImmutableList<BrushOperation> getOperations() {
        return operations;
    }

    public EventResult getStatus() {
        return status;
    }

    @NotNull
    @Override
    protected HandlerList<PlayerSnipeEvent> getHandlers() {
        return handlers;
    }

    /**
     * @return whether this event is custom. This means that the event uses custom operations
     */
    public boolean isCustom() {
        return isCustom;
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
