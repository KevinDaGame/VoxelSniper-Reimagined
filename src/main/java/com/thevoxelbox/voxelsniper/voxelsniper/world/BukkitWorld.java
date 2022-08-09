package com.thevoxelbox.voxelsniper.voxelsniper.world;

import com.thevoxelbox.voxelsniper.util.UndoDelegate;
import com.thevoxelbox.voxelsniper.voxelsniper.biome.VoxelBiome;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.BukkitChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.BukkitEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.IEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;

import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;

public record BukkitWorld(World world) implements IWorld {

    @Override
    public IBlock getBlock(ILocation location) {
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
    public void setBlock(ILocation location, IBlock block) {
    }

    @Override
    public IChunk getChunkAtLocation(int x, int z) {
        return new BukkitChunk(world.getChunkAt(x, z));
    }

    @Override
    public IChunk getChunkAtLocation(ILocation location) {
        return new BukkitChunk(world.getChunkAt(location.getBlockX(), location.getBlockZ()));
    }

    @Override
    public void refreshChunk(int x, int z) {
        world.refreshChunk(x, z);
    }

    @Override
    public void strikeLightning(ILocation location) {
        var bukkitloc = (BukkitLocation) location;
        world.strikeLightning(bukkitloc.getLocation());
    }

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public void spawn(ILocation location, IEntityType entity) {
        var bukkitloc = (BukkitLocation) location;
        world.spawnEntity(bukkitloc.getLocation(), ((BukkitEntityType) entity).getType());
    }

    @Override
    @Deprecated
    public void setBiome(int x, int z, VoxelBiome selectedBiome) {
        world.setBiome(x, z, Biome.valueOf(selectedBiome.key()));
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
    public void generateTree(ILocation location, TreeType treeType, UndoDelegate undoDelegate) {

        world.generateTree(((BukkitLocation) location).getLocation(), treeType, undoDelegate);
    }
}
