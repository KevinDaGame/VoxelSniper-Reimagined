package com.github.kevindagame.voxelsniper.location;

import com.github.kevindagame.voxelsniper.world.SpigotWorld;
import org.bukkit.Location;

public final class SpigotLocation {
    public static Location toSpigotLocation(BaseLocation location) {
        return new Location(((SpigotWorld) location.getWorld()).world(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }
}
