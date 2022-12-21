package com.github.kevindagame.voxelsniper.location;

import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;

public final class VoxelLocation extends BaseLocation implements Cloneable {

    public VoxelLocation(IWorld world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);
    }

    public VoxelLocation(IWorld world, double x, double y, double z) {
        super(world, x, y, z);
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

    public void add(BaseLocation location) {
        this.addX(location.getX());
        this.addY(location.getY());
        this.addZ(location.getZ());
    }

    public void add(VoxelVector direction) {
        this.addX(direction.getX());
        this.addY(direction.getY());
        this.addZ(direction.getZ());
    }

    public BaseLocation makeImmutable() {
        return new BaseLocation(world, x, y, z, yaw, pitch);
    }

    @Override
    public VoxelLocation makeMutable() {
        return this.clone();
    }

    @Override
    public VoxelLocation clone() {
        return (VoxelLocation) super.clone();
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
}
