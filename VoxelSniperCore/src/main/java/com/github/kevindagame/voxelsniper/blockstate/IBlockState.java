package com.github.kevindagame.voxelsniper.blockstate;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
import com.github.kevindagame.voxelsniper.world.IWorld;

public interface IBlockState {
    IBlock getBlock();

    IWorld getWorld();

    int getX();

    int getY();

    int getZ();

    VoxelMaterialType getMaterial();

    IBlockData getBlockData();

    BaseLocation getLocation();

    boolean update();

    boolean update(boolean force);

    boolean update(boolean force, boolean applyPhysics);
}
