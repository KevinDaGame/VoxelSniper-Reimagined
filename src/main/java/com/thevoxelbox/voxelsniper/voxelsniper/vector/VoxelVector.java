package com.thevoxelbox.voxelsniper.voxelsniper.vector;

import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.Objects;

public class VoxelVector {
    private double x;
    private double y;
    private double z;

    public VoxelVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VoxelVector() {
        this(0, 0, 0);
    }

    public VoxelLocation getLocation(IWorld world) {
        return new VoxelLocation(world, getX(), getY(), getZ());
    }

    public VoxelVector getMidpoint(VoxelVector other) {
        double x = (this.x + other.x) / 2.00;
        double y = (this.y + other.y) / 2.00;
        double z = (this.z + other.z) / 2.00;
        return new VoxelVector(x, y, z);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public VoxelVector add(VoxelVector vector) {
        this.setX(this.getX() + vector.getX());
        this.setY(this.getY() + vector.getY());
        this.setZ(this.getZ() + vector.getZ());
        return this;
    }
    public VoxelVector subtract(VoxelVector vector) {
        this.setX(this.getX() - vector.getX());
        this.setY(this.getY() - vector.getY());
        this.setZ(this.getZ() - vector.getZ());
        return this;
    }

    @Override
    public VoxelVector clone() {
        try {
            return (VoxelVector) super.clone();
        } catch (CloneNotSupportedException e) {
            return new VoxelVector(x, y, z);
        }
    }

    private double dot(VoxelVector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double angle(VoxelVector vector) {
        double dot = Math.min(Math.max(dot(vector) / (length() * vector.length()), -1), 1);

        return (float) Math.acos(dot);
    }

    public VoxelVector crossProduct(VoxelVector o) {
        double newX = y * o.z - o.y * z;
        double newY = z * o.x - o.z * x;
        double newZ = x * o.y - o.x * y;

        x = newX;
        y = newY;
        z = newZ;
        return this;
    }

    public void copy(VoxelVector vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }

    public void multiply(double m) {
        x *= m;
        y *= m;
        z *= m;
    }

    public VoxelVector normalize() {
        double length = length();

        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    public boolean isInSphere(VoxelVector target, int range) {
        return this.distanceSquared(target) <= square(range);
    }

    public double distance(VoxelVector vector) {
        return Math.sqrt(this.distanceSquared(vector));
    }

    public double distanceSquared(VoxelVector currentPoint) {
        return square(x - currentPoint.x) + square(y - currentPoint.y) + square(z - currentPoint.z);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared() {
        return square(x) + square(y) + square(z);
    }

    private static double square(double num) {
        return num * num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoxelVector that = (VoxelVector) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
