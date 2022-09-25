package com.thevoxelbox.voxelsniper.voxelsniper.chunk;

import com.thevoxelbox.voxelsniper.bukkit.BukkitVoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.BukkitEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

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
        ArrayList<IEntity> entities = new ArrayList<>();
        var chunkEntities = chunk.getEntities();
        for (Entity entity : chunkEntities) {
            entities.add(BukkitEntity.fromBukkitEntity(entity));
        }
        return entities;
    }
}
