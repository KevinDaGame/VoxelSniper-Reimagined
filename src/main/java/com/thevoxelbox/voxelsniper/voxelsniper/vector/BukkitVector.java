package com.thevoxelbox.voxelsniper.voxelsniper.vector;

import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.util.Vector;

public class BukkitVector implements IVector {
    private final Vector vector;
    public BukkitVector(Vector vector) {
        this.vector = vector;
    }

    @Override
    public ILocation getLocation(IWorld world) {
        return new BukkitLocation(vector.toLocation(((BukkitWorld)world).getWorld()));
    }

    @Override
    public IVector getMidpoint(IVector coordsTwo) {
        return new BukkitVector(vector.midpoint(((BukkitVector)coordsTwo).vector));
    }

    @Override
    public double getX() {
        return vector.getX();
    }

    @Override
    public double getY() {
        return vector.getY();
    }

    @Override
    public double getZ() {
        return vector.getZ();
    }

    @Override
    public int getBlockX() {
        return vector.getBlockX();
    }

    @Override
    public int getBlockY() {
        return vector.getBlockY();
    }

    @Override
    public int getBlockZ() {
        return vector.getBlockZ();
    }

    @Override
    public void setX(double x) {
        vector.setX(x);
    }

    @Override
    public void setY(double y) {
        vector.setY(y);
    }

    @Override
    public void setZ(double z) {
        vector.setZ(z);
    }

    @Override
    public IVector clone() {
        return new BukkitVector(vector.clone());
    }

    @Override
    public double angle(IVector vector) {
        return this.vector.angle(((BukkitVector)vector).vector);
    }

    @Override
    public double length() {
        return vector.length();
    }

    @Override
    public IVector crossProduct(IVector vector) {
        return new BukkitVector(this.vector.crossProduct(((BukkitVector)vector).vector));
    }

    @Override
    public void copy(IVector vector) {
        vector.copy(vector);
    }

    @Override
    public void multiply(double t) {
        vector.multiply(t);
    }

    @Override
    public double distance(IVector vector) {
        return this.vector.distance(((BukkitVector)vector).vector);
    }

    @Override
    public IVector normalize() {
        return new BukkitVector(vector.normalize());
    }

    @Override
    public double distanceSquared(IVector currentPoint) {
        return vector.distanceSquared(((BukkitVector)currentPoint).vector);
    }

    @Override
    public boolean isInSphere(IVector target, int range) {
        return vector.isInSphere(((BukkitVector)target).vector, range);
    }

    @Override
    public double lengthSquared() {
        return vector.lengthSquared();
    }

    public Vector getVector() {
        return vector;
    }
}
