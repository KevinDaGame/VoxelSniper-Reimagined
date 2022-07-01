package com.thevoxelbox.voxelsniper.voxelsniper.blockstate;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;

public interface IBlockState {
    IBlock getBlock();

    IWorld getWorld();

    int getX();

    int getY();

    int getZ();

    IMaterial getMaterial();

    IBlockData getBlockData();

    ILocation getLocation();

    boolean update();

    boolean update(boolean force);

    boolean update(boolean force, boolean applyPhysics);
}
