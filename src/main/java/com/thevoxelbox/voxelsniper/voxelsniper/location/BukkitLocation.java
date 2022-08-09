package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.BukkitChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.BukkitVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;

import org.bukkit.Location;

public class BukkitLocation extends AbstractLocation {
    private final Location location;

    public BukkitLocation(Location location) {
        super(new BukkitWorld(location.getWorld()));
        this.location = location;
    }

    @Override
    public int getBlockX() {
        return location.getBlockX();
    }

    @Override
    public int getBlockY() {
        return location.getBlockY();
    }

    @Override
    public int getBlockZ() {
        return location.getBlockZ();
    }

    @Override
    public double getX() {
        return location.getX();
    }

    @Override
    public double getY() {
        return location.getY();
    }

    @Override
    public double getZ() {
        return location.getZ();
    }

    @Override
    public void setX(double x) {
        location.setX(x);
    }

    @Override
    public void setY(double y) {
        location.setY(y);
    }

    @Override
    public void setZ(double z) {
        location.setZ(z);
    }

    @Override
    public float getYaw() {
        return location.getYaw();
    }

    @Override
    public float getPitch() {
        return location.getPitch();
    }

    @Override
    public void setYaw(float yaw) {
        location.setYaw(yaw);
    }

    @Override
    public void setPitch(float pitch) {
        location.setPitch(pitch);
    }

    @Override
    public BukkitWorld getWorld() {
        return new BukkitWorld(location.getWorld());
    }

    @Override
    public IBlock getClampedBlock() {
        return getWorld().getBlock(location.getBlockX(), Math.max(Math.min(getBlockY(), getWorld().getMaxWorldHeight()), getWorld().getMinWorldHeight()), location.getBlockZ());
    }

    @Override
    public IBlock getBlock() {
        return getWorld().getBlock(this);
    }

    @Override
    public IVector toVector() {
        return new BukkitVector(location.toVector());
    }

    @Override
    public double distanceSquared(ILocation targetLocation) {
        return location.distanceSquared(((BukkitLocation) targetLocation).getLocation());
    }

    @Override
    public ILocation clone() {
        return new BukkitLocation(location.clone());
    }

    @Override
    public IChunk getChunk() {
        return new BukkitChunk(location.getChunk());
    }

    @Override
    public double distance(ILocation location) {
        return this.location.distance(((BukkitLocation)location).getLocation());
    }

    public Location getLocation() {
        return this.location;
    }
}
