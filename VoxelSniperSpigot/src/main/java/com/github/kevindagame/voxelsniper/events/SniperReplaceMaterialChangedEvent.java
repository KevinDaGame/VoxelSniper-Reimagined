package com.github.kevindagame.voxelsniper.events;

import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import org.bukkit.event.HandlerList;

/**
 *
 */
public class SniperReplaceMaterialChangedEvent extends SniperMaterialChangedEvent {

    private static final HandlerList handlers = new HandlerList();

    public SniperReplaceMaterialChangedEvent(Sniper sniper, String toolId, IBlockData originalMaterial, IBlockData newMaterial) {
        super(sniper, toolId, originalMaterial, newMaterial);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
