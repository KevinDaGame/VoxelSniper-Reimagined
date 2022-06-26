package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.Location;

public class BukkitLocation extends AbstractLocation {
    private final Location location;

    public BukkitLocation(Location location) {
        super(new BukkitWorld(location.getWorld()));
        this.location = location;
    }

    @Override
    public int getX() {
        return location.getBlockX();
    }

    @Override
    public int getY() {
        return location.getBlockY();
    }

    @Override
    public int getZ() {
        return location.getBlockZ();
    }

    @Override
    public void setX(int x) {
        location.setX(x);
    }

    @Override
    public void setY(int y) {
        location.setY(y);
    }

    @Override
    public void setZ(int z) {
        location.setZ(z);
    }
}
