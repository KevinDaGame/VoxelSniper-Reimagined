package com.thevoxelbox.voxelsniper.voxelsniper.events.bukkit;

import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
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
