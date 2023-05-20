package com.github.kevindagame.voxelsniperforge.material;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public final class ItemMaterial extends AbstractForgeMaterial {
    private final Item item;

    public ItemMaterial(Item item, String namespace, String key) {
        super(namespace, key);
        this.item = item;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public boolean isBlock() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean hasGravity() {
        return false;
    }

    @Override
    public IBlockData createBlockData(String s) {
        throw new UnsupportedOperationException("Cannot create BlockData from Item");
    }

    @Override
    public IBlockData createBlockData() {
        throw new UnsupportedOperationException("Cannot create BlockData from Item");
    }

    @Override
    public boolean isAir() {
        return false;
    }

    @Override
    public boolean fallsOff() {
        return false;
    }

    @Override
    public boolean isFluid() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ItemMaterial) obj;
        return Objects.equals(this.item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }

    @Override
    ResourceLocation getResourceLocation() {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
