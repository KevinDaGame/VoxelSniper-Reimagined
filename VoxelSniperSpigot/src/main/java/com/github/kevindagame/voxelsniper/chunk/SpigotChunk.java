package com.github.kevindagame.voxelsniper.chunk;

import com.github.kevindagame.voxelsniper.SpigotVoxelSniper;
import com.github.kevindagame.voxelsniper.entity.SpigotEntity;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.world.IWorld;

import org.bukkit.Chunk;

import java.util.Arrays;

public class SpigotChunk implements IChunk {
    private final Chunk chunk;

    public SpigotChunk(Chunk chunk) {
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
        return SpigotVoxelSniper.getInstance().getWorld(chunk.getWorld());
    }

    @Override
    public Iterable<? extends IEntity> getEntities() {
        return Arrays.stream(chunk.getEntities()).map(SpigotEntity::fromSpigotEntity).toList();
    }
}
