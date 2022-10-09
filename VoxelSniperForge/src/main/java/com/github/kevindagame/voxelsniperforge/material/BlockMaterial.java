package com.github.kevindagame.voxelsniperforge.material;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;

public final class BlockMaterial extends AbstractForgeMaterial {
    private final Block block;

    public BlockMaterial(Block block) {
        this.block = block;
    }

    private Material getMaterial() {
        return block.defaultBlockState().getMaterial();
    }

    @Override
    public boolean isBlock() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return getMaterial().isSolid();
    }

    @Override
    public boolean isTransparent() {
        // TODO is this correct?
        return !getMaterial().isSolid();
    }

    @Override
    public boolean hasGravity() {
        return (block instanceof FallingBlock);
    }

    @Override
    public IBlockData createBlockData(String s) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IBlockData createBlockData() {
        var key = getResourceLocation();
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BlockMaterial) obj;
        return Objects.equals(this.block, that.block);
    }

    @Override
    public int hashCode() {
        return Objects.hash(block);
    }

    @Override
    public String toString() {
        return "BlockMaterial[" +
                "block=" + block + ']';
    }

    @Override
    ResourceLocation getResourceLocation() {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
