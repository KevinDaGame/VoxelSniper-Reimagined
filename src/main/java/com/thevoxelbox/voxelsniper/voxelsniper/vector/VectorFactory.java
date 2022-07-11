package com.thevoxelbox.voxelsniper.voxelsniper.vector;

import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.util.Vector;

public class VectorFactory {
    public static IVoxelsniper main;
    public static IVector getVector(double x, double y, double z) {
        if(main.getEnvironment() == Environment.BUKKIT) {
            //Will this throw an exception if it's never run on forge?
            return new BukkitVector(new Vector(x, y, z));
        }
        return null;
    }

    public static IVector getVector() {
        if(main.getEnvironment() == Environment.BUKKIT) {
            //Will this throw an exception if it's never run on forge?
            return new BukkitVector(new Vector());
        }
        return null;
    }
}
