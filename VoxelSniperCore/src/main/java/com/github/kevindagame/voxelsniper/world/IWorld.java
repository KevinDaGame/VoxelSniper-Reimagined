package com.github.kevindagame.voxelsniper.world;

import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.Iterator;
import java.util.List;

public interface IWorld {
    IBlock getBlock(VoxelLocation location);

    IBlock getBlock(int x, int y, int z);

    int getMinWorldHeight();

    int getMaxWorldHeight();

    /**
     * Note: Uses chunk coordinates (block coordinate/16)
     *
     * @param x coordinate
     * @param z coordinate
     * @return The Chunk
     */
    IChunk getChunkAtLocation(int x, int z);

    default IChunk getChunkAtLocation(VoxelLocation location) {
        return getChunkAtLocation(((int) Math.floor(location.getBlockX() / 16f)), ((int) Math.floor(location.getBlockZ() / 16f)));
    }

    /**
     * Returns a list of entities within a bounding box centered around a
     * Location.
     * <p>
     * This may not consider entities in currently unloaded chunks. Some
     * implementations may impose artificial restrictions on the size of the
     * search bounding box.
     *
     * @param location The center of the bounding box
     * @param x 1/2 the size of the box along x axis
     * @param y 1/2 the size of the box along y axis
     * @param z 1/2 the size of the box along z axis
     * @return the collection of entities near location. This will always be a
     *      non-null collection.
     */
    List<IEntity> getNearbyEntities(VoxelLocation location, double x, double y, double z);

    void refreshChunk(int x, int z);

    void strikeLightning(VoxelLocation location);

    String getName();

    void spawn(VoxelLocation location, VoxelEntityType entity);

    void setBiome(int x, int z, VoxelBiome selectedBiome);

    void setBiome(int x, int y, int z, VoxelBiome selectedBiome);

    int getHighestBlockYAt(int x, int z);

    void regenerateChunk(int x, int z);

    Undo generateTree(VoxelLocation location, VoxelTreeType treeType, Undo undo);

    Iterator<IBlock> getBlockIterator(VoxelVector origin, VoxelVector direction, double yOffset, int maxDistance);
}
