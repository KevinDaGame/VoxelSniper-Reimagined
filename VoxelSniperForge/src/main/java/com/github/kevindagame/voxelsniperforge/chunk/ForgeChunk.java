package com.github.kevindagame.voxelsniperforge.chunk;

import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.world.IWorld;
import com.github.kevindagame.voxelsniperforge.world.ForgeWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;

public class ForgeChunk implements IChunk {
    private final LevelChunk chunk;
    private final ForgeWorld level;

    public ForgeChunk(LevelChunk chunkAt, ForgeWorld level) {
        this.chunk = chunkAt;
        this.level = level;
    }

    @Override
    public int getX() {
        return chunk.getPos().x;
    }

    @Override
    public int getZ() {
        return chunk.getPos().z;
    }

    @Override
    public IWorld getWorld() {
        return new ForgeWorld(getLevel());
    }

    public LevelChunk getChunk() {
        return chunk;
    }

    public ServerLevel getLevel() {
        return level.getLevel();
    }

    @Override
    public Iterable<? extends IEntity> getEntities() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
