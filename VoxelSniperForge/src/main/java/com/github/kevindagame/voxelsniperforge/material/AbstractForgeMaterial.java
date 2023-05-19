package com.github.kevindagame.voxelsniperforge.material;

import com.github.kevindagame.voxelsniper.material.IMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import net.minecraft.resources.ResourceLocation;

public abstract class AbstractForgeMaterial implements IMaterial {

    abstract ResourceLocation getResourceLocation();

    @Override
    public final String getKey() {
        var key = getResourceLocation();
        if (key == null) throw new IllegalStateException("Key is null");
        return key.toString();
    }

    @Override
    public final String getName() {
        var key = getResourceLocation();
        if (key == null) throw new IllegalStateException("Key is null");
        return key.getPath();
    }

    @Override
    public final boolean equals(String key) {
        return this.getKey().equals(key);
    }

    @Override
    public final boolean equals(VoxelMaterial material) {
        return this.getKey().equals(material.getKey());
    }
}
