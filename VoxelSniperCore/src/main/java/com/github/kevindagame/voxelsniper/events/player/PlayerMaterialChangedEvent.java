package com.github.kevindagame.voxelsniper.events.player;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.HandlerList;

public class PlayerMaterialChangedEvent extends PlayerEvent {

    private static final HandlerList<PlayerMaterialChangedEvent> handlers = new HandlerList<>();
    private final IBlockData oldMaterial;
    private final IBlockData newMaterial;

    public PlayerMaterialChangedEvent(IPlayer p, IBlockData oldMaterial, IBlockData newMaterial) {
        super(p);
        this.oldMaterial = oldMaterial;
        this.newMaterial = newMaterial;
    }

    public IBlockData getOldMaterial() {
        return newMaterial;
    }

    public IBlockData getNewMaterial() {
        return newMaterial;
    }

    @Override
    protected HandlerList<? extends PlayerMaterialChangedEvent> getHandlers() {
        return handlers;
    }
    public static HandlerList<? extends PlayerMaterialChangedEvent> getHandlerList() {
        return handlers;
    }
}
