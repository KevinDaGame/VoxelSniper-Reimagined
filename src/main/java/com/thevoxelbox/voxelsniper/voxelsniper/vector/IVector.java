package com.thevoxelbox.voxelsniper.voxelsniper.vector;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.util.Vector;

public interface IVector {
    ILocation getLocation(IWorld world);

    IVector getMidpoint(IVector coordsTwo);
    double getX();
    double getY();
    double getZ();
    int getBlockX();
    int getBlockY();
    int getBlockZ();
    void setX(double x);
    void setY(double y);
    void setZ(double z);
    IVector clone();
    default IVector add(IVector vector) {
        this.setX(this.getX() + vector.getX());
        this.setY(this.getY() + vector.getY());
        this.setZ(this.getZ() + vector.getZ());
        return this;
    }
    default IVector subtract(IVector vector) {
        this.setX(this.getX() - vector.getX());
        this.setY(this.getY() - vector.getY());
        this.setZ(this.getZ() - vector.getZ());
        return this;
    }
    double angle(IVector vector);
    double length();
    IVector crossProduct(IVector vector);

    void copy(IVector perpendicularOne);

    void multiply(double t);

    double distance(IVector vector);

    IVector normalize();
}
