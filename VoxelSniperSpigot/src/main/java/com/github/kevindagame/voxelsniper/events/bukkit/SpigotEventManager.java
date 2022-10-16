package com.github.kevindagame.voxelsniper.events.bukkit;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.events.IEventManager;
import org.bukkit.Bukkit;

public class SpigotEventManager implements IEventManager {

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
    @Override
    public void callSniperSnipeEvent(Sniper owner, IBrush brush) {
        SniperSnipeEvent event = new SniperSnipeEvent(owner, brush);
        Bukkit.getPluginManager().callEvent(event);
    }
}
