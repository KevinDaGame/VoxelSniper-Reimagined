package com.github.kevindagame.voxelsniper.events;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

public interface IEventManager {

    void callSniperReplaceMaterialChangedEvent(Sniper sniper, String toolId, IBlockData oldSubstance, IBlockData newSubstance);

    void callSniperMaterialChangedEvent(Sniper sniper, String toolId, IBlockData oldSubstance, IBlockData newSubstance);

    void callSniperBrushChangedEvent(Sniper owner, String currentToolId, IBrush originalBrush, IBrush newBrush);

    void callSniperBrushSizeChangedEvent(Sniper sniper, String currentToolId, int originalSize, int newSize);

    void callSniperSnipeEvent(Sniper sniper, IBrush brush, boolean success);
}
