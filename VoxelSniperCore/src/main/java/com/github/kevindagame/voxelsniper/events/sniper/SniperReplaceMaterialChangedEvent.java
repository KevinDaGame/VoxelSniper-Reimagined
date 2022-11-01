package com.github.kevindagame.voxelsniper.events.sniper;

import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.events.HandlerList;

public class SniperReplaceMaterialChangedEvent extends SniperMaterialChangedEvent {

    private static final HandlerList<SniperReplaceMaterialChangedEvent> handlers = new HandlerList<>();

    public SniperReplaceMaterialChangedEvent(Sniper sniper, IBlockData originalMaterial, IBlockData newMaterial) {
        super(sniper, originalMaterial, newMaterial);
    }

    public static HandlerList<SniperReplaceMaterialChangedEvent> getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList<SniperReplaceMaterialChangedEvent> getHandlers() {
        return handlers;
    }
}
