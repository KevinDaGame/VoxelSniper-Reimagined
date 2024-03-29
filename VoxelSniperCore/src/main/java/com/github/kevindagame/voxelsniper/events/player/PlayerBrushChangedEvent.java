package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.Cancellable;
import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerBrushChangedEvent extends PlayerEvent<PlayerBrushChangedEvent> implements Cancellable {

    private static final HandlerList<PlayerBrushChangedEvent> handlers = new HandlerList<>();
    private final @Nullable IBrush oldBrush;
    private final @NotNull IBrush newBrush;
    private final String toolId;
    private EventResult status;

    public PlayerBrushChangedEvent(IPlayer p, String toolId, @Nullable IBrush oldBrush, @NotNull IBrush newBrush) {
        super(p);
        this.oldBrush = oldBrush;
        this.newBrush = newBrush;
        this.toolId = toolId;
    }

    public static void registerListener(Consumer<PlayerBrushChangedEvent> handler) {
        handlers.registerListener(handler);
    }

    public static void registerListener(EventPriority priority, Consumer<PlayerBrushChangedEvent> handler) {
        handlers.registerListener(priority, handler);
    }

    @Nullable
    public IBrush getOldBrush() {
        return oldBrush;
    }

    @NotNull
    public IBrush getNewBrush() {
        return newBrush;
    }

    public String getToolId() {
        return toolId;
    }

    @Override
    protected HandlerList<PlayerBrushChangedEvent> getHandlers() {
        return handlers;
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
