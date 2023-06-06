package com.github.kevindagame.voxelsniper.world;

import com.github.kevindagame.util.brushOperation.BrushOperation;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.block.SpigotBlock;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.chunk.SpigotChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.SpigotEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.location.SpigotLocation;
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public record SpigotWorld(World world) implements IWorld {
    private static final Random RANDOM = new Random();

    @Override
    public IBlock getBlock(BaseLocation location) {
        return new SpigotBlock(world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @Override
    public IBlock getBlock(int x, int y, int z) {
        return new SpigotBlock(world.getBlockAt(x, y, z));
    }

    @Override
    public int getMinWorldHeight() {
        return world.getMinHeight();
    }

    @Override
    public int getMaxWorldHeight() {
        return world.getMaxHeight();
    }

    @Override
    public IChunk getChunkAtLocation(int x, int z) {
        return new SpigotChunk(world.getChunkAt(x, z));
    }

    @Override
    public List<IEntity> getNearbyEntities(BaseLocation location, double x, double y, double z) {
        return this.world.getNearbyEntities(SpigotLocation.toSpigotLocation(location), x, y, z).stream().map(SpigotEntity::fromSpigotEntity).toList();
    }

    @Override
    public void refreshChunk(int x, int z) {
        world.refreshChunk(x, z);
    }

    @Override
    public void strikeLightning(BaseLocation location) {
        world.strikeLightning(SpigotLocation.toSpigotLocation(location));
    }

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public void spawn(BaseLocation location, VoxelEntityType entity) {
        world.spawnEntity(SpigotLocation.toSpigotLocation(location), EntityType.valueOf(entity.getKey().toUpperCase(Locale.ROOT)));
    }

    @Override
    public void setBiome(int x, int z, VoxelBiome selectedBiome) {
        world.setBiome(x, z, Biome.valueOf(selectedBiome.key().toUpperCase()));
    }

    @Override
    public void setBiome(int x, int y, int z, VoxelBiome selectedBiome) {
        world.setBiome(x, y, z, Biome.valueOf(selectedBiome.key().toUpperCase()));
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        return world.getHighestBlockYAt(x, z);
    }

    @Override
    @Deprecated
    public void regenerateChunk(int x, int z) {
        world.regenerateChunk(x, z);
    }

    @Override
    public List<BrushOperation> generateTree(BaseLocation location, VoxelTreeType treeType, boolean updateBlocks) {
        if (treeType.isSupported()) {
            TreeType bukkitType = TreeType.valueOf(treeType.name());
            var loc = SpigotLocation.toSpigotLocation(location);
            SpigotBlockLogger logger = new SpigotBlockLogger(this, updateBlocks);
            try {
                // This is a better implementation that handles tile entities (bee nests) better, but is not supported below MC 1.18
                this.world.generateTree(loc, RANDOM, bukkitType, logger);
            } catch (Exception e) {
                // fallback implementation in case the above implementation is unavailable
                this.world.generateTree(loc, bukkitType, logger);
            }
            return logger.operations;
        }
        return null;
    }

    @Override
    public Iterator<IBlock> getBlockIterator(VoxelVector origin, VoxelVector direction, double yOffset, int maxDistance) {
        BlockIterator bukkitIterator = new BlockIterator(this.world, new Vector(origin.getX(), origin.getY(), origin.getZ()), new Vector(direction.getX(), direction.getY(), direction.getZ()), yOffset, maxDistance);
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return bukkitIterator.hasNext();
            }

            @Override
            public IBlock next() {
                return new SpigotBlock(bukkitIterator.next());
            }
        };
    }

    @Override
    public VoxelBiome getBiome(BaseLocation location) {
        return VoxelBiome.getBiome(world.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ()).getKey().getKey());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpigotWorld that = (SpigotWorld) o;

        return world.equals(that.world);
    }

    @Override
    public int hashCode() {
        return world.hashCode();
    }
}
