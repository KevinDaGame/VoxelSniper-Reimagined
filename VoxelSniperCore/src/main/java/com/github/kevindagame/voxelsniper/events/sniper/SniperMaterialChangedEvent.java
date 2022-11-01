package com.github.kevindagame.voxelsniper.events.sniper;

import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.events.HandlerList;

public class SniperMaterialChangedEvent extends SniperEvent {

    private static final HandlerList<SniperMaterialChangedEvent> handlers = new HandlerList<>();
    private final IBlockData originalMaterial;
    private final IBlockData newMaterial;

    public SniperMaterialChangedEvent(Sniper sniper, IBlockData originalMaterial, IBlockData newMaterial) {
        super(sniper);
        this.originalMaterial = originalMaterial;
        this.newMaterial = newMaterial;
    }

    public static HandlerList<? extends SniperMaterialChangedEvent> getHandlerList() {
        return handlers;
    }

    public IBlockData getOriginalMaterial() {
        return originalMaterial;
    }

    public IBlockData getNewMaterial() {
        return newMaterial;
    }

    @Override
    public HandlerList<? extends SniperMaterialChangedEvent> getHandlers() {
        return handlers;
    }
}
