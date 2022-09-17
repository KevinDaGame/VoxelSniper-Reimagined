package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import org.bukkit.Location;

public class LocationFactory {
    public static ILocation getLocation(IWorld world, double x, double y, double z) {
        if(VoxelSniper.voxelsniper.getEnvironment() == Environment.BUKKIT) {
        //Will this throw an exception if it's never run on forge?
            return new BukkitLocation(new Location(((BukkitWorld)world).world(), x, y, z));
        }
        return null;
    }

    public static ILocation getLocation(IWorld world, double x, double y, double z, float yaw, float pitch) {
        if(VoxelSniper.voxelsniper.getEnvironment() == Environment.BUKKIT) {
            //Will this throw an exception if it's never run on forge?
            return new BukkitLocation(new Location(((BukkitWorld)world).world(), x, y, z, yaw, pitch));
        }
        return null;
    }
}
