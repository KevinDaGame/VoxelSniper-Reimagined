package com.thevoxelbox.voxelsniper.voxelsniper.material;

import org.bukkit.Material;

public class BukkitMaterial implements IMaterial {
    private final Material material;

    public BukkitMaterial(Material material) {
        this.material = material;
    }

    @Override
    public boolean isSolid() {
        return material.isSolid();
    }
}
