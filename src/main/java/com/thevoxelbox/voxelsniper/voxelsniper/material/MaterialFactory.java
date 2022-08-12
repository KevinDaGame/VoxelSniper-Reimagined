package com.thevoxelbox.voxelsniper.voxelsniper.material;

import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Environment;

import org.bukkit.Material;

public class MaterialFactory {
    public static IMaterial getMaterial(VoxelMaterial material) {
        if(VoxelSniper.voxelsniper.getEnvironment() == Environment.BUKKIT) {
            return new BukkitMaterial(Material.matchMaterial(material.getKey()), material);
        }
        return null;
    }
}
