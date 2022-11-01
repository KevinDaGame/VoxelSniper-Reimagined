package com.github.kevindagame.voxelsniper.events.sniper;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.events.HandlerList;

import org.jetbrains.annotations.NotNull;

public class SniperSnipeEvent extends SniperEvent {

    private static final HandlerList<SniperSnipeEvent> handlers = new HandlerList<>();
    private final IBrush brush;

    private final boolean success;
    public SniperSnipeEvent(Sniper sniper, IBrush brush, boolean success) {
        super(sniper);
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
    public HandlerList<SniperSnipeEvent> getHandlers() {
        return handlers;
    }
    public static HandlerList<SniperSnipeEvent> getHandlerList() {
        return handlers;
    }
}
