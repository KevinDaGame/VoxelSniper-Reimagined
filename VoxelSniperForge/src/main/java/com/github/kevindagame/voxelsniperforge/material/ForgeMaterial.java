package com.github.kevindagame.voxelsniperforge.material;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.IMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import net.minecraft.world.level.material.Material;

public record ForgeMaterial(Material material) implements IMaterial {

    @Override
    public boolean isSolid() {
        return material().isSolid();
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public boolean equals(String key) {
        return false;
    }

    @Override
    public IBlockData createBlockData() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean equals(VoxelMaterial material) {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return material.;
    }

    @Override
    public boolean isBlock() {
        return false;
    }

    @Override
    public boolean hasGravity() {
        return false;
    }

    @Override
    public IBlockData createBlockData(String s) {
        return null;
    }
}
