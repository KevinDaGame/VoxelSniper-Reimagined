package com.thevoxelbox.voxelsniper.voxelsniper.chunk;

import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.BukkitEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

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
    public IBlock getBlock(int x, int y, int z) {
        return new BukkitBlock(chunk.getWorld().getBlockAt(getX() + x, y, getZ() + z));
    }

    @Override
    public Iterable<? extends IEntity> getEntities() {
        ArrayList<IEntity> entities = new ArrayList<>();
        var chunkEntities = chunk.getEntities();
        for (Entity entity : chunkEntities) {
            entities.add(new BukkitEntity(entity));
        }
        return entities;
    }
}
