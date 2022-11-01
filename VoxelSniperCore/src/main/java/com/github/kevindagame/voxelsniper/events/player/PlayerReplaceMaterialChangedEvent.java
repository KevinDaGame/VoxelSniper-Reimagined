package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.HandlerList;

public class PlayerReplaceMaterialChangedEvent extends PlayerMaterialChangedEvent {

    private static final HandlerList<PlayerReplaceMaterialChangedEvent> handlers = new HandlerList<>();

    public PlayerReplaceMaterialChangedEvent(IPlayer p, IBlockData originalMaterial, IBlockData newMaterial) {
        super(p, originalMaterial, newMaterial);
    }

    public static HandlerList<PlayerReplaceMaterialChangedEvent> getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList<PlayerReplaceMaterialChangedEvent> getHandlers() {
        return handlers;
    }
}
