package com.thevoxelbox.voxelsniper.voxelsniper.events;

import com.thevoxelbox.voxelsniper.brush.IBrush;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;

public interface IEventManager {

    void callSniperReplaceMaterialChangedEvent(Sniper sniper, String toolId, IBlockData oldSubstance, IBlockData newSubstance);
    void callSniperMaterialChangedEvent(Sniper sniper, String toolId, IBlockData oldSubstance, IBlockData newSubstance);

    void callSniperBrushChangedEvent(Sniper owner, String currentToolId, IBrush originalBrush, IBrush newBrush);

    void callSniperBrushSizeChangedEvent(Sniper sniper, String currentToolId, int originalSize, int newSize);
}
