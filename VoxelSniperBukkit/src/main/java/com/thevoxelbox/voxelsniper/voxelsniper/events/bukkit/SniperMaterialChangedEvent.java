package com.thevoxelbox.voxelsniper.voxelsniper.events.bukkit;

import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 */
public class SniperMaterialChangedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Sniper sniper;
    private final IBlockData originalMaterial;
    private final IBlockData newMaterial;
    private final String toolId;

    public SniperMaterialChangedEvent(Sniper sniper, String toolId, IBlockData originalMaterial, IBlockData newMaterial) {
        this.sniper = sniper;
        this.originalMaterial = originalMaterial;
        this.newMaterial = newMaterial;
        this.toolId = toolId;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public IBlockData getOriginalMaterial() {
        return originalMaterial;
    }

    public IBlockData getNewMaterial() {
        return newMaterial;
    }

    public Sniper getSniper() {
        return sniper;
    }

    public String getToolId() {
        return toolId;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
