package com.thevoxelbox.voxelsniper.voxelsniper.blockstate;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

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
