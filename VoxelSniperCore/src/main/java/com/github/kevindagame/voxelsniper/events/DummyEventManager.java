package com.github.kevindagame.voxelsniper.events;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

public class DummyEventManager implements IEventManager {
    @Override
    public void callSniperReplaceMaterialChangedEvent(Sniper sniper, String toolId, IBlockData oldSubstance, IBlockData newSubstance) {
    }

    @Override
    public void callSniperMaterialChangedEvent(Sniper sniper, String toolId, IBlockData oldSubstance, IBlockData newSubstance) {
    }

    @Override
    public void callSniperBrushChangedEvent(Sniper owner, String currentToolId, IBrush originalBrush, IBrush newBrush) {
    }

    @Override
    public void callSniperBrushSizeChangedEvent(Sniper sniper, String currentToolId, int originalSize, int newSize) {
    }

    @Override
    public void callSniperSnipeEvent(Sniper sniper, IBrush brush, boolean success) {
    }
}
