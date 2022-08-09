package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import org.bukkit.Location;

public class LocationFactory {
    public static IVoxelsniper main;
    public static ILocation getLocation(IWorld world, int x, int y, int z) {
        if(main.getEnvironment() == Environment.BUKKIT) {
        //Will this throw an exception if it's never run on forge?
            return new BukkitLocation(new Location(((BukkitWorld)world).world(), x, y, z));
        }
        return null;
    }
}
