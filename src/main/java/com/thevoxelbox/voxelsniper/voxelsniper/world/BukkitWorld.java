package com.thevoxelbox.voxelsniper.voxelsniper.world;

import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.voxelsniper.biome.VoxelBiome;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.BukkitChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.treeType.VoxelTreeType;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VoxelVector;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.Locale;

public record BukkitWorld(World world) implements IWorld {

    @Override
    public IBlock getBlock(VoxelLocation location) {
        return new BukkitBlock(world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @Override
    public IBlock getBlock(int x, int y, int z) {
        return new BukkitBlock(world.getBlockAt(x, y, z));
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
        return new BukkitChunk(world.getChunkAt(x / 16, z / 16));
    }

    @Override
    public IChunk getChunkAtLocation(VoxelLocation location) {
        return new BukkitChunk(world.getChunkAt(((int) Math.floor(location.getBlockX() / 16f)), ((int) Math.floor(location.getBlockZ() / 16f))));
    }

    @Override
    public void refreshChunk(int x, int z) {
        world.refreshChunk(x, z);
    }

    @Override
    public void strikeLightning(VoxelLocation location) {
        world.strikeLightning(BukkitLocation.toBukkitLocation(location));
    }

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public void spawn(VoxelLocation location, VoxelEntityType entity) {
        world.spawnEntity(BukkitLocation.toBukkitLocation(location), EntityType.valueOf(entity.getKey().toUpperCase(Locale.ROOT)));
    }

    @Override
    @Deprecated
    public void setBiome(int x, int z, VoxelBiome selectedBiome) {
        world.setBiome(x, z, Biome.valueOf(selectedBiome.key().toUpperCase()));
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
    public void generateTree(VoxelLocation location, VoxelTreeType treeType, Undo undo) {
        if (treeType.isSupported()) {
            BukkitUndoDelegate undoDelegate = new BukkitUndoDelegate(world, undo);
            TreeType bukkitType = TreeType.valueOf(treeType.name());
            world.generateTree(BukkitLocation.toBukkitLocation(location), bukkitType, undoDelegate);
        }
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
                return new BukkitBlock(bukkitIterator.next());
            }
        };
    }
}
