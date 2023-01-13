package com.github.kevindagame.voxelsniper.blockstate;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.block.SpigotBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData;
import com.github.kevindagame.voxelsniper.blockstate.sign.SpigotSign;
import com.github.kevindagame.voxelsniper.material.SpigotMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class SpigotBlockState extends AbstractBlockState {
    protected final BlockState blockState;

    public SpigotBlockState(IBlock block, BlockState blockState) {
        super(block);
        this.blockState = blockState;
    }

    public static SpigotBlockState fromSpigotState(IBlock block, BlockState state) {
        if (state instanceof Sign sign)
            return new SpigotSign(block, sign);
        return new SpigotBlockState(block, state);
    }

    public static IBlockState fromSpigotState(BlockState state) {
        return SpigotBlockState.fromSpigotState(new SpigotBlock(state.getBlock()), state);
    }

    @Override
    public VoxelMaterial getMaterial() {
        return SpigotMaterial.fromSpigotMaterial(blockState.getType());
    }

    @Override
    public IBlockData getBlockData() {
        return SpigotBlockData.fromSpigotData(blockState.getBlockData());
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
