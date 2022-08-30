package com.thevoxelbox.voxelsniper.voxelsniper.blockstate;

import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.BukkitBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.sign.BukkitSign;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class BukkitBlockState implements IBlockState {
    protected final BlockState blockState;

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
    public VoxelMaterial getMaterial() {
        return BukkitMaterial.fromBukkitMaterial(blockState.getType());
    }

    @Override
    public IBlockData getBlockData() {
        return new BukkitBlockData(blockState.getBlockData());
    }

    @Override
    public ILocation getLocation() {
        return new BukkitLocation(blockState.getLocation());
    }

    @Override
    public boolean update() {
        return blockState.update();
    }

    @Override
    public boolean update(boolean force) {
        return blockState.update(force);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        return blockState.update(force, applyPhysics);
    }

    public static BukkitBlockState fromBukkitState(BlockState state) {
        if (state instanceof Sign sign)
            return new BukkitSign(sign);
        return new BukkitBlockState(state);
    }
}
