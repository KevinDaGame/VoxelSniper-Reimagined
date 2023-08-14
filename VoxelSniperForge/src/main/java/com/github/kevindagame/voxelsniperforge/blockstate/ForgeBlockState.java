package com.github.kevindagame.voxelsniperforge.blockstate;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockstate.AbstractBlockState;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.blockdata.ForgeBlockData;
import com.github.kevindagame.voxelsniperforge.blockstate.sign.ForgeSign;
import com.github.kevindagame.voxelsniperforge.material.BlockMaterial;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class ForgeBlockState extends AbstractBlockState {
    protected final BlockState blockState;

    public ForgeBlockState(ForgeBlock block, BlockState blockState) {
        super(block);
        this.blockState = blockState;
    }

    public static ForgeBlockState fromForgeBlock(ForgeBlock block, BlockState blockState, BlockEntity tileEntity) {
        if (tileEntity instanceof SignBlockEntity sign)
            return new ForgeSign(block, blockState, sign);
        return new ForgeBlockState(block, blockState);
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
        if (this.getWorld() == null) {
            return true;
        }
        IBlock block = getBlock();

        if (block.getMaterial() != this.getMaterial() && !force) {
            return false;
        }

        try {
            block.setBlockData(getBlockData(), applyPhysics);
            return true;
        } catch (Exception e) {
            VoxelSniper.voxelsniper.getLogger().log(java.util.logging.Level.WARNING, "Failed to set BlockData", e);
            return false;
        }
    }

    public void refreshSnapshot() {
        // we might need this in the future
        // this.load(tileEntity);
    }

    public void setFlag(int flags) {
        // we might need this in the future
    }

    public void setWorldHandle(ServerLevel level) {
        // we might need this in the future
    }
}
