package com.thevoxelbox.voxelsniper.voxelsniper.vector;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.LocationFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.Objects;

public class VoxelVector implements IVector {
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

    @Override
    public ILocation getLocation(IWorld world) {
        return LocationFactory.getLocation(world, getX(), getY(), getZ());
    }

    @Override
    public VoxelVector getMidpoint(VoxelVector other) {
        x = (this.x + other.x) / 2;
        y = (this.y + other.y) / 2;
        z = (this.x + other.z) / 2;
        return this;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public VoxelVector clone() {
        return new VoxelVector(x, y, z);
    }

    private double dot(VoxelVector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    @Override
    public double angle(VoxelVector vector) {
        double dot = Math.min(Math.max(dot(vector) / (length() * vector.length()), -1), 1);

        return (float) Math.acos(dot);
    }

    @Override
    public VoxelVector crossProduct(VoxelVector o) {
        double newX = y * o.z - o.y * z;
        double newY = z * o.x - o.z * x;
        double newZ = x * o.y - o.x * y;

        x = newX;
        y = newY;
        z = newZ;
        return this;
    }

    @Override
    public void copy(VoxelVector vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }

    @Override
    public void multiply(double m) {
        x *= m;
        y *= m;
        z *= m;
    }

    @Override
    public VoxelVector normalize() {
        double length = length();

        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    @Override
    public boolean isInSphere(VoxelVector target, int range) {
        return this.distanceSquared(target) <= square(range);
    }

    @Override
    public double distance(VoxelVector vector) {
        return Math.sqrt(this.distanceSquared(vector));
    }

    @Override
    public double distanceSquared(VoxelVector currentPoint) {
        return square(x - currentPoint.x) + square(y - currentPoint.y) + square(z - currentPoint.z);
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
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
