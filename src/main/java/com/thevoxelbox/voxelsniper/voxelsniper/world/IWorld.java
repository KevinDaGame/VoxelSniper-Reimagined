package com.thevoxelbox.voxelsniper.voxelsniper.world;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;

public interface IWorld {
    IBlock getBlock(ILocation location);
    IBlock getBlock(int x, int y, int z);
    int getMinWorldHeight();
    int getMaxWorldHeight();
    IBlock setBlock(ILocation location, IBlock block);

}
