package com.github.kevindagame.voxelsniper.blockstate;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;

public interface IBlockState {
    IBlock getBlock();

    IWorld getWorld();

    int getX();

    int getY();

    int getZ();

    VoxelMaterial getMaterial();

    IBlockData getBlockData();

    VoxelLocation getLocation();

    boolean update();

    boolean update(boolean force);

    boolean update(boolean force, boolean applyPhysics);
}
