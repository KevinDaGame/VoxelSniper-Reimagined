package com.github.kevindagame.voxelsniper.events;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.events.sniper.SniperEvent;

public class SniperBrushChangedEvent extends SniperEvent {

    private static final HandlerList<SniperBrushChangedEvent> handlers = new HandlerList<>();
    private final IBrush originalBrush;
    private final IBrush newBrush;
    private final String toolId;

    public SniperBrushChangedEvent(Sniper sniper, String toolId, IBrush originalBrush, IBrush newBrush) {
        super(sniper);
        this.originalBrush = originalBrush;
        this.newBrush = newBrush;
        this.toolId = toolId;
    }

    public static HandlerList<SniperBrushChangedEvent> getHandlerList() {
        return handlers;
    }

    public IBrush getOriginalBrush() {
        return originalBrush;
    }

    public IBrush getNewBrush() {
        return newBrush;
    }

    public String getToolId() {
        return toolId;
    }

    @Override
    public HandlerList<SniperBrushChangedEvent> getHandlers() {
        return handlers;
    }
}
