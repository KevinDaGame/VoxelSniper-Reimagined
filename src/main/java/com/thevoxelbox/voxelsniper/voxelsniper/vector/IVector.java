package com.thevoxelbox.voxelsniper.voxelsniper.vector;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

public interface IVector {
    ILocation getLocation(IWorld world);

    VoxelVector getMidpoint(VoxelVector coordsTwo);
    double getX();
    double getY();
    double getZ();
    default int getBlockX() {
        return (int) Math.floor(getX());
    }
    default int getBlockY() {
        return (int) Math.floor(getY());
    }
    default int getBlockZ() {
        return (int) Math.floor(getZ());
    }
    void setX(double x);
    void setY(double y);
    void setZ(double z);
    VoxelVector clone();
    default VoxelVector add(VoxelVector vector) {
        this.setX(this.getX() + vector.getX());
        this.setY(this.getY() + vector.getY());
        this.setZ(this.getZ() + vector.getZ());
        return (VoxelVector) this;
    }
    default VoxelVector subtract(VoxelVector vector) {
        this.setX(this.getX() - vector.getX());
        this.setY(this.getY() - vector.getY());
        this.setZ(this.getZ() - vector.getZ());
        return (VoxelVector) this;
    }
    double angle(VoxelVector vector);
    double length();
    VoxelVector crossProduct(VoxelVector vector);

    void copy(VoxelVector perpendicularOne);

    void multiply(double t);

    double distance(VoxelVector vector);

    VoxelVector normalize();

    double distanceSquared(VoxelVector currentPoint);

    boolean isInSphere(VoxelVector target, int range);

    double lengthSquared();
}
