package com.thevoxelbox.voxelsniper.voxelsniper.material;

import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import org.bukkit.Material;

public class MaterialFactory {
    public static IVoxelsniper main;
    public static IMaterial getMaterial(VoxelMaterial material) {
        if(main.getEnvironment() == Environment.BUKKIT) {
            //todo should this be uppercase?
            return new BukkitMaterial(Material.getMaterial(material.getKey().toUpperCase()));
        }
        return null;
    }
}
