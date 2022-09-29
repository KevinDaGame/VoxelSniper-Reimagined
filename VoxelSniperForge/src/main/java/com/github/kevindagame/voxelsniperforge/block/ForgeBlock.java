package com.github.kevindagame.voxelsniperforge.block;

import com.github.kevindagame.voxelsniper.block.AbstractBlock;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends AbstractBlock {


    @Override
    public void setMaterial(VoxelMaterial material) {

    }

    @Override
    public void setMaterial(VoxelMaterial material, boolean applyPhysics) {

    }

    @Nullable
    @Override
    public BlockFace getFace(IBlock block) {
        return null;
    }

    @Override
    public IBlockData getBlockData() {
        return null;
    }

    @Override
    public void setBlockData(IBlockData blockData) {

    }

    @Override
    public void setBlockData(IBlockData blockData, boolean applyPhysics) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isLiquid() {
        return false;
    }

    @Override
    public IBlockState getState() {
        return null;
    }

    @Override
    public boolean isBlockFacePowered(BlockFace face) {
        return false;
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        return false;
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        return false;
    }

    @Override
    public boolean isBlockPowered() {
        return false;
    }
}
