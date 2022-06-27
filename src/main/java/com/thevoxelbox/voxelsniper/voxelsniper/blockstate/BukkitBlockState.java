package com.thevoxelbox.voxelsniper.voxelsniper.blockstate;

import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.BukkitBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.block.BlockState;

public class BukkitBlockState implements IBlockState {
    private BlockState blockState;

    public BukkitBlockState(BlockState blockState) {
        this.blockState = blockState;
    }

    @Override
    public IBlock getBlock() {
        return new BukkitBlock(blockState.getBlock());
    }

    @Override
    public IWorld getWorld() {
        return new BukkitWorld(blockState.getWorld());
    }

    @Override
    public int getX() {
        return blockState.getX();
    }

    @Override
    public int getY() {
        return blockState.getY();
    }

    @Override
    public int getZ() {
        return blockState.getZ();
    }

    @Override
    public IMaterial getMaterial() {
        return new BukkitMaterial(blockState.getType());
    }

    @Override
    public IBlockData getBlockData() {
        return new BukkitBlockData(blockState.getBlockData());
    }
}
