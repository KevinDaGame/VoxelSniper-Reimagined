package com.thevoxelbox.voxelsniper.voxelsniper.material;

import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.BukkitBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;

import org.bukkit.Material;

public record BukkitMaterial(Material material) implements IMaterial, Comparable<BukkitMaterial> {

    @Override
    public boolean isSolid() {
        return material.isSolid();
    }

    @Override
    public String getKey() {
        return this.material.getKey().toString();
    }

    @Override
    public boolean equals(String key) {
        return getKey().equals(key);
    }

    @Override
    public IBlockData createBlockData() {
        return new BukkitBlockData(material.createBlockData());
    }

    @Override
    public String getName() {
        return material.name();
    }

    @Override
    public boolean equals(VoxelMaterial material) {
        return this.material.getKey().getNamespace().equals(material.getNamespace()) && this.material.getKey().getKey().equals(material.getKey());
    }

    @Override
    public boolean isTransparent() {
        return material.isTransparent();
    }

    @Override
    public boolean isBlock() {
        return material.isBlock();
    }

    @Override
    public VoxelMaterial getVoxelMaterial() {
        return VoxelMaterial.getMaterial(this.material().getKey().getNamespace(), this.material().getKey().getKey());
    }

    @Override
    public boolean hasGravity() {
        return material.hasGravity();
    }

    @Override
    public IBlockData createBlockData(String s) {
        return new BukkitBlockData(material.createBlockData(s));
    }

    @Override
    public int compareTo(BukkitMaterial o) {
        return material.compareTo(o.material);
    }
}
