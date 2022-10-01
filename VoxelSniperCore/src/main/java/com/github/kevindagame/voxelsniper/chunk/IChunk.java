package com.github.kevindagame.voxelsniper.chunk;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.world.IWorld;

public interface IChunk {
    int getX();

    int getZ();

    IWorld getWorld();

    default IBlock getBlock(int x, int y, int z) {
        return this.getWorld().getBlock((getX() * 16) + x, y, (getZ() * 16) + z);
    }

    Iterable<? extends IEntity> getEntities();
}
