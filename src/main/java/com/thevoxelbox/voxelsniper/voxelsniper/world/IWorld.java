package com.thevoxelbox.voxelsniper.voxelsniper.world;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;

public interface IWorld {
    IBlock getBlock(ILocation location);
    IBlock getBlock(int x, int y, int z);
    int getMinWorldHeight();
    int getMaxWorldHeight();
    void setBlock(ILocation location, IBlock block);

    IChunk getChunkAtLocation(int x, int z);
    IChunk getChunkAtLocation(ILocation location);
    void refreshChunk(int x, int z);
}
