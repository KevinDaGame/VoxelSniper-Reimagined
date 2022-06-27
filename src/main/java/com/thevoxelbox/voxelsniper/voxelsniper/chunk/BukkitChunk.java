package com.thevoxelbox.voxelsniper.voxelsniper.chunk;

import org.bukkit.Chunk;

public class BukkitChunk implements IChunk {
    private Chunk chunk;

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
}
