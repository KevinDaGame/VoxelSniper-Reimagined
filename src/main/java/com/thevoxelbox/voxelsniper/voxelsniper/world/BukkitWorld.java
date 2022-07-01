package com.thevoxelbox.voxelsniper.voxelsniper.world;

import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.BukkitChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import org.bukkit.World;

public class BukkitWorld implements IWorld {
    private final World world;

    public BukkitWorld(World world) {
        this.world = world;
    }

    @Override
    public IBlock getBlock(ILocation location) {
        return new BukkitBlock(world.getBlockAt(location.getX(), location.getY(), location.getZ()));
    }

    @Override
    public IBlock getBlock(int x, int y, int z) {
        return new BukkitBlock(world.getBlockAt(x, y, z));
    }

    @Override
    public int getMinWorldHeight() {
        return 0;
    }

    @Override
    public int getMaxWorldHeight() {
        return 0;
    }

    @Override
    public void setBlock(ILocation location, IBlock block) {
        return;
    }

    @Override
    public IChunk getChunkAtLocation(int x, int z) {
        return new BukkitChunk(world.getChunkAt(x, z));
    }

    @Override
    public IChunk getChunkAtLocation(ILocation location) {
        return new BukkitChunk(world.getChunkAt(location.getX(), location.getZ()));
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

    public World getWorld() {
        return world;
    }
}
