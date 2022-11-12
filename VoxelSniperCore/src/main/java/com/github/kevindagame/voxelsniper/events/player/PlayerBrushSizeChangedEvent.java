package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.HandlerList;

public class PlayerBrushSizeChangedEvent extends PlayerEvent {

    private static final HandlerList<PlayerBrushSizeChangedEvent> handlers = new HandlerList<>();
    private final int oldSize;
    private final int newSize;
    private final String toolId;

    public PlayerBrushSizeChangedEvent(IPlayer p, String toolId, int oldSize, int newSize) {
        super(p);
        this.oldSize = oldSize;
        this.newSize = newSize;
        this.toolId = toolId;
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

    @Override
    protected HandlerList<PlayerBrushSizeChangedEvent> getHandlers() {
        return handlers;
    }
    public static HandlerList<PlayerBrushSizeChangedEvent> getHandlerList() {
        return handlers;
    }
}
