package com.github.kevindagame.voxelsniperforge.blockstate;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockstate.AbstractBlockState;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.blockdata.ForgeBlockData;
import com.github.kevindagame.voxelsniperforge.material.BlockMaterial;

import net.minecraft.world.level.block.state.BlockState;


public class ForgeBlockState extends AbstractBlockState {
    protected final BlockState blockState;

    public ForgeBlockState(ForgeBlock block) {
        super(block);
        this.blockState = null;
    }

    public static ForgeBlockState fromForgeBlock(ForgeBlock block) {
//        if (state instanceof Sign sign)
//            return new SpigotSign(block);
        return new ForgeBlockState(block);
    }

    @Override
    public VoxelMaterial getMaterial() {
        return BlockMaterial.fromForgeBlock(this.blockState.getBlock());
    }

    @Override
    public IBlockData getBlockData() {
        return ForgeBlockData.fromData(this.blockState);
    }

    @Override
    public boolean update() {
        return this.update(false);
    }

    @Override
    public boolean update(boolean force) {
        return this.update(force, true);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
