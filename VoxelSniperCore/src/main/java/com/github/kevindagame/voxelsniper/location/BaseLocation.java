package com.github.kevindagame.voxelsniper.location;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.Objects;

public class BaseLocation implements Cloneable {

    protected final IWorld world;
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;

    public BaseLocation(IWorld world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public BaseLocation(IWorld world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    private static double square(double num) {
        return num * num;
    }

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public final double getZ() {
        return this.z;
    }

    public final int getBlockX() {
        return (int) Math.floor(getX());
    }

    public final int getBlockY() {
        return (int) Math.floor(getY());
    }

    public final int getBlockZ() {
        return (int) Math.floor(getZ());
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public final IWorld getWorld() {
        return this.world;
    }

    @Override
    public BaseLocation clone() {
        try {
            return (BaseLocation) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    public VoxelLocation makeMutable() {
        return new VoxelLocation(world, x, y, z, yaw, pitch);
    }

    @Deprecated
    public final IBlock getClampedBlock() {
        return getWorld().getBlock(getBlockX(), Math.max(Math.min(getBlockY(), getWorld().getMaxWorldHeight()), getWorld().getMinWorldHeight()), getBlockZ());
    }

    public final IBlock getBlock() {
        return getWorld().getBlock(this);
    }

    public final VoxelVector toVector() {
        return new VoxelVector(x, y, z);
    }

    public final IChunk getChunk() {
        return world.getChunkAtLocation(this);
    }

    public final double distanceSquared(BaseLocation targetLocation) {
        return square(x - targetLocation.getX()) + square(y - targetLocation.getY()) + square(z - targetLocation.getZ());
    }

    public final double distance(BaseLocation location) {
        return Math.sqrt(distanceSquared(location));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseLocation that = (BaseLocation) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.z, z) == 0 && Float.compare(that.yaw, yaw) == 0 && Float.compare(that.pitch, pitch) == 0 && world.equals(that.world);
    }

    @Override
    public String toString() {
        return "BaseLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }

    @Override
    public final int hashCode() {
        return Objects.hash(world, x, y, z, yaw, pitch);
    }

    public final VoxelVector getDirection() {
        VoxelVector vector = new VoxelVector();

        double rotX = this.getYaw();
        double rotY = this.getPitch();

        vector.setY(-Math.sin(Math.toRadians(rotY)));

        double xz = Math.cos(Math.toRadians(rotY));

        vector.setX(-xz * Math.sin(Math.toRadians(rotX)));
        vector.setZ(xz * Math.cos(Math.toRadians(rotX)));

        return vector;
    }
}
