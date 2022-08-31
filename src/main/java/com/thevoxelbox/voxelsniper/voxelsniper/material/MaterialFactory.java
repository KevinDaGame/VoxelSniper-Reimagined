package com.thevoxelbox.voxelsniper.voxelsniper.material;

import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Environment;

import org.bukkit.Material;

public class MaterialFactory {
    public static IMaterial getMaterial(VoxelMaterial material) {
        if (VoxelSniper.voxelsniper.getEnvironment() == Environment.BUKKIT) {
            Material mat = Material.matchMaterial(material.getKey());
            if (mat != null) return new BukkitMaterial(mat);
        }
        return null;
    }
}
