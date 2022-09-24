package com.thevoxelbox.voxelsniper.voxelsniper.world;

import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.voxelsniper.biome.VoxelBiome;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VoxelVector;

import java.util.Iterator;

import org.bukkit.TreeType;

public interface IWorld {
    IBlock getBlock(VoxelLocation location);
    IBlock getBlock(int x, int y, int z);
    int getMinWorldHeight();
    int getMaxWorldHeight();
    void setBlock(VoxelLocation location, IBlock block);

    IChunk getChunkAtLocation(int x, int z);
    IChunk getChunkAtLocation(VoxelLocation location);
    void refreshChunk(int x, int z);

    void strikeLightning(VoxelLocation location);

    String getName();
    void spawn(VoxelLocation location, VoxelEntityType entity);
    void setBiome(int x, int z, VoxelBiome selectedBiome);

    int getHighestBlockYAt(int x, int z);

    void regenerateChunk(int x, int z);

    void generateTree(VoxelLocation location, TreeType treeType, Undo undo);

    Iterator<IBlock> getBlockIterator(VoxelVector origin, VoxelVector direction, double yOffset, int maxDistance);
}
