package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.HandlerList;

public class PlayerBrushSizeChangedEvent extends PlayerEvent {

    private static final HandlerList<PlayerBrushSizeChangedEvent> handlers = new HandlerList<>();
    private final int originalSize;
    private final int newSize;
    private final String toolId;

    public PlayerBrushSizeChangedEvent(IPlayer p, String toolId, int originalSize, int newSize) {
        super(p);
        this.originalSize = originalSize;
        this.newSize = newSize;
        this.toolId = toolId;
    }

    public static HandlerList<PlayerBrushSizeChangedEvent> getHandlerList() {
        return handlers;
    }

    public int getOriginalSize() {
        return originalSize;
    }

    public int getNewSize() {
        return newSize;
    }

    public String getToolId() {
        return toolId;
    }

    @Override
    public HandlerList<PlayerBrushSizeChangedEvent> getHandlers() {
        return handlers;
    }
}
