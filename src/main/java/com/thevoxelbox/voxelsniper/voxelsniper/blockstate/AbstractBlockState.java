package com.thevoxelbox.voxelsniper.voxelsniper.blockstate;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

public abstract class AbstractBlockState implements IBlockState {
    private final IBlock block;

    protected AbstractBlockState(IBlock block) {
        this.block = block;
    }

    @Override
    public final IBlock getBlock() {
        return this.block;
    }

    @Override
    public final VoxelLocation getLocation() {
        return new VoxelLocation(getWorld(), getX(), getY(), getZ());
    }

    @Override
    public IWorld getWorld() {
        return block.getWorld();
    }

    @Override
    public int getX() {
        return block.getX();
    }

    @Override
    public int getY() {
        return block.getY();
    }

    @Override
    public int getZ() {
        return block.getZ();
    }
}
