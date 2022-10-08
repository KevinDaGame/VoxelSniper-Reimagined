package com.github.kevindagame.voxelsniperforge.material;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.IMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;

public record ForgeMaterial(Item item) implements IMaterial {

    private Block getBlock() {
        if (!isBlock()) throw new UnsupportedOperationException("Item is not a block");
        return Block.byItem(item);
    }

    private Material getMaterial() {
        return getBlock().defaultBlockState().getMaterial();
    }

    @Override
    public boolean isBlock() {
        return (item instanceof BlockItem);
    }

    @Override
    public boolean isSolid() {
        return isBlock() && getMaterial().isSolid();
    }

    @Override
    public boolean isTransparent() {
        // TODO is this correct?
        return isBlock() && !getMaterial().isSolid();
    }

    @Override
    public boolean hasGravity() {
        return isBlock() && (getBlock() instanceof FallingBlock);
    }

    @Override
    public String getKey() {
        var key = ForgeRegistries.ITEMS.getKey(item);
        if (key == null) throw new IllegalStateException("Key is null");
        return key.toString();
    }

    @Override
    public String getName() {
        var key = ForgeRegistries.ITEMS.getKey(item);
        if (key == null) throw new IllegalStateException("Key is null");
        return key.getPath();
    }

    @Override
    public IBlockData createBlockData(String s) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IBlockData createBlockData() {
        ForgeRegistries.ITEMS.getKey(item);
//        Block b = getBlock();
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean equals(String key) {
        return this.getKey().equals(key);
    }

    @Override
    public boolean equals(VoxelMaterial material) {
        return this.getKey().equals(material.getKey());
    }
}
