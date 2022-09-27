package com.github.kevindagame.voxelsniper.chunk;

import com.github.kevindagame.voxelsniper.BukkitVoxelSniper;
import com.github.kevindagame.voxelsniper.entity.BukkitEntity;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.world.IWorld;

import org.bukkit.Chunk;

import java.util.Arrays;

public class BukkitChunk implements IChunk {
    private final Chunk chunk;

    public BukkitChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public int getX() {
        return chunk.getX();
    }

    @Override
    public int getZ() {
        return chunk.getZ();
    }

    @Override
    public IWorld getWorld() {
        return BukkitVoxelSniper.getInstance().getWorld(chunk.getWorld());
    }

    @Override
    public Iterable<? extends IEntity> getEntities() {
        return Arrays.stream(chunk.getEntities()).map(BukkitEntity::fromBukkitEntity).toList();
    }
}
