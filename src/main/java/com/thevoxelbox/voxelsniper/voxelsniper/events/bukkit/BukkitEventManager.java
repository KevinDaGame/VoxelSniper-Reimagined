package com.thevoxelbox.voxelsniper.voxelsniper.events.bukkit;

import com.thevoxelbox.voxelsniper.brush.IBrush;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.events.IEventManager;

import org.bukkit.Bukkit;

public class BukkitEventManager implements IEventManager {

    @Override
    public void callSniperReplaceMaterialChangedEvent(Sniper sniper, String toolId, IBlockData oldSubstance, IBlockData newSubstance) {
        Bukkit.getPluginManager().callEvent(new SniperReplaceMaterialChangedEvent(sniper, toolId, oldSubstance, newSubstance));
    }

    @Override
    public void callSniperMaterialChangedEvent(Sniper sniper, String toolId, IBlockData oldSubstance, IBlockData newSubstance) {
        Bukkit.getPluginManager().callEvent(new SniperMaterialChangedEvent(sniper, toolId, oldSubstance, newSubstance));
    }

    @Override
    public void callSniperBrushChangedEvent(Sniper owner, String currentToolId, IBrush originalBrush, IBrush newBrush) {
        SniperBrushChangedEvent event = new SniperBrushChangedEvent(owner, currentToolId, originalBrush, newBrush);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void callSniperBrushSizeChangedEvent(Sniper sniper, String currentToolId, int originalSize, int newSize) {
        SniperBrushSizeChangedEvent event = new SniperBrushSizeChangedEvent(sniper, currentToolId, originalSize, newSize);
        Bukkit.getPluginManager().callEvent(event);
    }
}
