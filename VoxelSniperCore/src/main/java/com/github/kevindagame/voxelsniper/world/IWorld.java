package com.github.kevindagame.voxelsniper.world;

import com.github.kevindagame.util.brushOperation.BrushOperation;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

public interface IWorld {
    IBlock getBlock(BaseLocation location);

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

    default IChunk getChunkAtLocation(BaseLocation location) {
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
    List<IEntity> getNearbyEntities(BaseLocation location, double x, double y, double z);

    void refreshChunk(int x, int z);

    void strikeLightning(BaseLocation location);

    String getName();

    void spawn(BaseLocation location, VoxelEntityType entity);

    void setBiome(int x, int z, VoxelBiome selectedBiome);

    void setBiome(int x, int y, int z, VoxelBiome selectedBiome);

    int getHighestBlockYAt(int x, int z);

    void regenerateChunk(int x, int z);

    @Nullable
    List<BrushOperation> generateTree(BaseLocation location, VoxelTreeType treeType, boolean updateBlocks);

    @Nullable
    default List<BrushOperation> generateTree(BaseLocation location, VoxelTreeType treeType) {
        return generateTree(location, treeType, true);
    }

    Iterator<IBlock> getBlockIterator(VoxelVector origin, VoxelVector direction, double yOffset, int maxDistance);

    VoxelBiome getBiome(BaseLocation location);
}
