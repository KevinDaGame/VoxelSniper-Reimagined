package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.HandlerList;

public class PlayerBrushChangedEvent extends PlayerEvent {

    private static final HandlerList<PlayerBrushChangedEvent> handlers = new HandlerList<>();
    private final IBrush oldBrush;
    private final IBrush newBrush;
    private final String toolId;

    public PlayerBrushChangedEvent(IPlayer p, String toolId, IBrush oldBrush, IBrush newBrush) {
        super(p);
        this.oldBrush = oldBrush;
        this.newBrush = newBrush;
        this.toolId = toolId;
    }

    public IBrush getOldBrush() {
        return oldBrush;
    }

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
    public static HandlerList<PlayerBrushChangedEvent> getHandlerList() {
        return handlers;
    }
}
