package com.github.kevindagame.voxelsniper.location;

import com.github.kevindagame.voxelsniper.world.BukkitWorld;
import org.bukkit.Location;

public final class BukkitLocation {
    public static Location toBukkitLocation(VoxelLocation location) {
        return new Location(((BukkitWorld) location.getWorld()).world(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }
}
