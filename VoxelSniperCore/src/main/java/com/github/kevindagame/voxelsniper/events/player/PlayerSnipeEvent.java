package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import org.jetbrains.annotations.NotNull;

public class PlayerSnipeEvent extends PlayerEvent {

    private static final HandlerList<PlayerSnipeEvent> handlers = new HandlerList<>();
    private final IBrush brush;

    private final boolean success;
    public PlayerSnipeEvent(IPlayer p, IBrush brush, boolean success) {
        super(p);
        this.brush = brush;
        this.success = success;
    }

    public IBrush getBrush() {
        return brush;
    }

    public boolean isSuccessful() {
        return success;
    }

    @NotNull
    @Override
    public HandlerList<PlayerSnipeEvent> getHandlers() {
        return handlers;
    }
    public static HandlerList<PlayerSnipeEvent> getHandlerList() {
        return handlers;
    }
}
