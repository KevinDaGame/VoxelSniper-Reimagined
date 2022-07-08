package com.thevoxelbox.voxelsniper.voxelsniper.vector;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.util.Vector;

public class VoxelVector implements IVector {
    private final double x;
    private final double y;
    private final double z;

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
        return null;
    }

    @Override
    public IVector getMidpoint(IVector coordsTwo) {
        return null;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getZ() {
        return 0;
    }

    @Override
    public int getBlockX() {
        return 0;
    }

    @Override
    public int getBlockY() {
        return 0;
    }

    @Override
    public int getBlockZ() {
        return 0;
    }

    @Override
    public void setX(double x) {

    }

    @Override
    public void setY(double y) {

    }

    @Override
    public void setZ(double z) {

    }

    @Override
    public IVector clone() {
        return null;
    }

    @Override
    public double angle(IVector vector) {
        return 0;
    }

    @Override
    public double length() {
        return 0;
    }

    @Override
    public IVector crossProduct(IVector vector) {
        return null;
    }

    @Override
    public void copy(IVector perpendicularOne) {

    }

    @Override
    public void multiply(double t) {

    }

    @Override
    public double distance(IVector vector) {
        return 0;
    }
}
