package com.thevoxelbox.voxelsniper.util;

import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class LocationWrapper extends Location {

    public LocationWrapper(World world, double x, double y, double z) {
        super(world, x, y, z);
    }
    public LocationWrapper(Location l) {
        super(l.getWorld(), l.getX(), l.getY(), l.getZ());
    }

    public void setBlockData(BlockData blockData, boolean b) {
        getClampedBlock().setBlockData(blockData, b);
    }

    public Block getClampedBlock() {
        return Objects.requireNonNull(getWorld()).getBlockAt(getBlockX(), getYClamped(), getBlockZ());
    }

    public Block getOffsetBlock(int x, int y, int z) {
        return Objects.requireNonNull(getWorld()).getBlockAt(getBlockX() + x, getBlockY() + y, getBlockZ() + z);
    }

    public Block getOffsetClampedBlock(int x, int y, int z) {
        return Objects.requireNonNull(getWorld()).getBlockAt(getBlockX() + x, getYClamped(y), getBlockZ() + z);
    }
    public int getYClamped() {
        return Math.max(Objects.requireNonNull(this.getWorld()).getMinHeight(), Math.min(getBlockY(), this.getWorld().getMaxHeight()));
    }
    public int getYClamped(int offsetY) {
        return Math.max(Objects.requireNonNull(this.getWorld()).getMinHeight(), Math.min(getBlockY() + offsetY, this.getWorld().getMaxHeight()));
    }
    public void addX(int x) {
        setX(getX() + x);
    }
    public void addY(int y) {
        setY(getY() + y);
    }
    public void addZ(int z) {
        setZ(getZ() + z);
    }
}
