package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VoxelVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.Objects;

public class VoxelLocation {

    private final IWorld world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public VoxelLocation(IWorld world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public VoxelLocation(IWorld world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    private static double square(double num) {
        return num * num;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getBlockX() {
        return (int) Math.floor(getX());
    }

    public int getBlockY() {
        return (int) Math.floor(getY());
    }

    public int getBlockZ() {
        return (int) Math.floor(getZ());
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void addX(double x) {
        this.setX(this.getX() + x);
    }

    public void addY(double y) {
        this.setY(this.getY() + y);
    }

    public void addZ(double z) {
        this.setZ(this.getZ() + z);
    }

    public void add(int x, int y, int z) {
        this.addX(x);
        this.addY(y);
        this.addZ(z);
    }

    public void add(VoxelLocation location) {
        this.addX(location.getX());
        this.addY(location.getY());
        this.addZ(location.getZ());
    }

    public void add(VoxelVector direction) {
        this.addX(direction.getX());
        this.addY(direction.getY());
        this.addZ(direction.getZ());
    }

    public IWorld getWorld() {
        return this.world;
    }

    @Override
    public VoxelLocation clone() {
        try {
            return (VoxelLocation) super.clone();
        } catch (CloneNotSupportedException e) {
            return new VoxelLocation(world, this.x, this.y, this.z, this.yaw, this.pitch);
        }
    }

    public IBlock getClampedBlock() {
        return getWorld().getBlock(getBlockX(), Math.max(Math.min(getBlockY(), getWorld().getMaxWorldHeight()), getWorld().getMinWorldHeight()), getBlockZ());
    }

    public IBlock getBlock() {
        return getWorld().getBlock(this);
    }

    public VoxelVector toVector() {
        return new VoxelVector(x, y, z);
    }

    public IChunk getChunk() {
        return world.getChunkAtLocation(this);
    }

    public double distanceSquared(VoxelLocation targetLocation) {
        return square(x - targetLocation.getX()) + square(y - targetLocation.getY()) + square(z - targetLocation.getZ());
    }

    public double distance(VoxelLocation location) {
        return Math.sqrt(distanceSquared(location));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoxelLocation that = (VoxelLocation) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.z, z) == 0 && Float.compare(that.yaw, yaw) == 0 && Float.compare(that.pitch, pitch) == 0 && world.equals(that.world);
    }

    @Override
    public String toString() {
        return "VoxelLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, y, z, yaw, pitch);
    }

    public VoxelVector getDirection() {
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
