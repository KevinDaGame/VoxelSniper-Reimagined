package com.thevoxelbox.voxelsniper.voxelsniper.world;

import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
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
    public IBlock setBlock(ILocation location, IBlock block) {
        return null;
    }
}
