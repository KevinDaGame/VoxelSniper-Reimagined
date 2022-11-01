package com.github.kevindagame.voxelsniper.events.sniper;

import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.events.HandlerList;

public class SniperBrushSizeChangedEvent extends SniperEvent {

    private static final HandlerList<SniperBrushSizeChangedEvent> handlers = new HandlerList<>();
    private final int originalSize;
    private final int newSize;
    private final String toolId;

    public SniperBrushSizeChangedEvent(Sniper sniper, String toolId, int originalSize, int newSize) {
        super(sniper);
        this.originalSize = originalSize;
        this.newSize = newSize;
        this.toolId = toolId;
    }

    public static HandlerList<SniperBrushSizeChangedEvent> getHandlerList() {
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
    public HandlerList<SniperBrushSizeChangedEvent> getHandlers() {
        return handlers;
    }
}
