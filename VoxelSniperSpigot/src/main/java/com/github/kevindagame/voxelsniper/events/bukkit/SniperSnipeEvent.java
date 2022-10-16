package com.github.kevindagame.voxelsniper.events.bukkit;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.snipe.Sniper;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SniperSnipeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Sniper sniper;
    private final IBrush brush;
    public SniperSnipeEvent(Sniper sniper, IBrush brush) {
        this.sniper = sniper;
        this.brush = brush;
    }

    public Sniper getSniper() {
        return sniper;
    }

    public IBrush getBrush() {
        return brush;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
