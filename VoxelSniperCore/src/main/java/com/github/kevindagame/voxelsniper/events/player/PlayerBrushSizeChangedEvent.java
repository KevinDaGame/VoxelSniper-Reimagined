package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.Cancellable;
import com.github.kevindagame.voxelsniper.events.EventPriority;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import java.util.function.Consumer;

public class PlayerBrushSizeChangedEvent extends PlayerEvent<PlayerBrushSizeChangedEvent> implements Cancellable {

    private static final HandlerList<PlayerBrushSizeChangedEvent> handlers = new HandlerList<>();
    private final int oldSize;
    private final int newSize;
    private final String toolId;
    private final IBrush brush;
    private EventResult status;

    public PlayerBrushSizeChangedEvent(IPlayer p, String toolId, IBrush brush, int oldSize, int newSize) {
        super(p);
        this.oldSize = oldSize;
        this.newSize = newSize;
        this.toolId = toolId;
        this.brush = brush;
    }

    public int getOldSize() {
        return oldSize;
    }

    public int getNewSize() {
        return newSize;
    }

    public String getToolId() {
        return toolId;
    }

    public IBrush getBrush() {
        return brush;
    }

    @Override
    protected HandlerList<PlayerBrushSizeChangedEvent> getHandlers() {
        return handlers;
    }

    public static void registerListener(Consumer<PlayerBrushSizeChangedEvent> handler) {
        handlers.registerListener(handler);
    }

    public static void registerListener(EventPriority priority, Consumer<PlayerBrushSizeChangedEvent> handler) {
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
