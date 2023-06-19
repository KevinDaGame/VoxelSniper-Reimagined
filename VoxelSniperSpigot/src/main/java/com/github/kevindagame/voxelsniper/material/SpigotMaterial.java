package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class SpigotMaterial extends VoxelMaterial {
    private final Material material;

    public SpigotMaterial(Material material) {
        super(material.getKey().getNamespace(), material.getKey().getKey());
        this.material = material;
    }

    public static VoxelMaterial fromSpigotMaterial(Material type) {
        if (type == null || type.isAir()) return VoxelMaterial.AIR();
        return VoxelMaterial.getMaterial(type.getKey().getNamespace(), type.getKey().getKey());
    }

    @NotNull
    @Override
    public String getKey() {
        return this.material.getKey().toString();
    }

    @Override
    public IBlockData createBlockData() {
        return SpigotBlockData.fromSpigotData(material.createBlockData());
    }

    @Override
    public boolean isAir() {
        return material.isAir();
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
    public IBlockData createBlockData(String s) {
        return SpigotBlockData.fromSpigotData(material.createBlockData(s));
    }

    public Material getMaterial() {
        return material;
    }
}
