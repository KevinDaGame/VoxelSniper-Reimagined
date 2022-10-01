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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean equals(String key) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IBlockData createBlockData() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean equals(VoxelMaterial material) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isTransparent() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isBlock() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean hasGravity() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public IBlockData createBlockData(String s) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
