package com.thevoxelbox.voxelsniper.voxelsniper.blockstate;

import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.BukkitBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.sign.BukkitSign;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class BukkitBlockState extends AbstractBlockState {
    protected final BlockState blockState;

    public BukkitBlockState(IBlock block, BlockState blockState) {
        super(block);
        this.blockState = blockState;
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

    public static BukkitBlockState fromBukkitState(IBlock block, BlockState state) {
        if (state instanceof Sign sign)
            return new BukkitSign(block, sign);
        return new BukkitBlockState(block, state);
    }

    public static IBlockState fromBukkitState(BlockState state) {
        return fromBukkitState(new BukkitBlock(state.getBlock()), state);
    }
}
