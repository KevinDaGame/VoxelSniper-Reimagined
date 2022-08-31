package com.thevoxelbox.voxelsniper.voxelsniper.world;

import com.thevoxelbox.voxelsniper.util.UndoDelegate;
import com.thevoxelbox.voxelsniper.voxelsniper.biome.VoxelBiome;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;

import org.bukkit.TreeType;

public interface IWorld {
    IBlock getBlock(ILocation location);
    IBlock getBlock(int x, int y, int z);
    int getMinWorldHeight();
    int getMaxWorldHeight();
    void setBlock(ILocation location, IBlock block);

    IChunk getChunkAtLocation(int x, int z);
    IChunk getChunkAtLocation(ILocation location);
    void refreshChunk(int x, int z);

    void strikeLightning(ILocation location);

    String getName();
    void spawn(ILocation location, VoxelEntityType entity);
    void setBiome(int x, int z, VoxelBiome selectedBiome);

    int getHighestBlockYAt(int x, int z);

    void regenerateChunk(int x, int z);

    void generateTree(ILocation location, TreeType treeType, UndoDelegate undoDelegate);
}
