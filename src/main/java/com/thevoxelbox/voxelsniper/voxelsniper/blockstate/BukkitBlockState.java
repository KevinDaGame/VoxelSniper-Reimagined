package com.thevoxelbox.voxelsniper.voxelsniper.blockstate;

import com.thevoxelbox.voxelsniper.bukkit.BukkitVoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.BukkitBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.sign.BukkitSign;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
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
        return BukkitVoxelSniper.getInstance().getWorld(blockState.getWorld());
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
        return BukkitBlockData.fromBukkitData(blockState.getBlockData());
    }

    @Override
    public VoxelLocation getLocation() {
        return new VoxelLocation(getWorld(), getX(), getY(), getZ());
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
