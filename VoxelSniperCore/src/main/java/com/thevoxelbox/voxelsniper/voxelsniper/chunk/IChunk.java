package com.thevoxelbox.voxelsniper.voxelsniper.chunk;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

public interface IChunk {
    int getX();

    int getZ();

    IWorld getWorld();

    default IBlock getBlock(int x, int y, int z) {
        return this.getWorld().getBlock((getX() * 16) + x, y, (getZ() * 16) + z);
    }

    Iterable<? extends IEntity> getEntities();
}
