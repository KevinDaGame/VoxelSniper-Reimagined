package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.Event;

public abstract class PlayerEvent<T extends PlayerEvent<?>> extends Event<T> {
    private final IPlayer player;

    public PlayerEvent(IPlayer p) {
        this.player = p;
    }

    public final IPlayer getPlayer() {
        return player;
    }
}
