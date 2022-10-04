package com.github.kevindagame.voxelsniperforge.block;

import com.github.kevindagame.voxelsniper.block.AbstractBlock;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends AbstractBlock {

    private final Block block;

    public ForgeBlock(VoxelLocation location, ResourceLocation material) {
        super(location, VoxelMaterial.getMaterial(material.getNamespace(), material.getPath()));
        this.block = ForgeRegistries.BLOCKS.getValue(material);
    }

    @Override
    public void setMaterial(VoxelMaterial material) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setMaterial(VoxelMaterial material, boolean applyPhysics) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Nullable
    @Override
    public BlockFace getFace(IBlock block) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IBlockData getBlockData() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setBlockData(IBlockData blockData) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setBlockData(IBlockData blockData, boolean applyPhysics) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isLiquid() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IBlockState getState() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isBlockFacePowered(BlockFace face) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isBlockPowered() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
