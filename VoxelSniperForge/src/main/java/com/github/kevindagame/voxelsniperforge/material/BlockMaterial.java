package com.github.kevindagame.voxelsniperforge.material;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniperforge.blockdata.ForgeBlockData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public final class BlockMaterial extends AbstractForgeMaterial {
    private final Block block;

    public BlockMaterial(Block block, String namespace, String key) {
        super(namespace, key);
        this.block = block;
    }

    public static VoxelMaterial fromForgeBlock(Block block) {
        var tag = ForgeRegistries.BLOCKS.getKey(block);
        if (tag == null || block.defaultBlockState().isAir()) return VoxelMaterial.AIR();
        return VoxelMaterial.getMaterial(tag.getNamespace(), tag.getPath());
    }

    public Block getBlock() {
        return block;
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
        return ForgeBlockData.createNewData(this, s);
    }

    @Override
    public IBlockData createBlockData() {
        return this.createBlockData(null);
    }

    @Override
    public boolean isAir() {
        return block.defaultBlockState().isAir();
    }

    @Override
    public boolean fallsOff() {
        return false;
    }

    @Override
    public boolean isFluid() {
        throw new UnsupportedOperationException("Not supported yet.");
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
    ResourceLocation getResourceLocation() {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
