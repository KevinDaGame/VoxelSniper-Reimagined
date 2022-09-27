package com.github.kevindagame.voxelsniper.blockstate;

import com.github.kevindagame.voxelsniper.block.BukkitBlock;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.BukkitBlockData;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockstate.sign.BukkitSign;
import com.github.kevindagame.voxelsniper.material.BukkitMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class BukkitBlockState extends AbstractBlockState {
    protected final BlockState blockState;

    public BukkitBlockState(IBlock block, BlockState blockState) {
        super(block);
        this.blockState = blockState;
    }

    public static BukkitBlockState fromBukkitState(IBlock block, BlockState state) {
        if (state instanceof Sign sign)
            return new BukkitSign(block, sign);
        return new BukkitBlockState(block, state);
    }

    public static IBlockState fromBukkitState(BlockState state) {
        return fromBukkitState(new BukkitBlock(state.getBlock()), state);
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
}
