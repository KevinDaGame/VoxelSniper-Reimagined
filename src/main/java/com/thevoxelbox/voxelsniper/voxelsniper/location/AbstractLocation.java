package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.Objects;

public abstract class AbstractLocation implements ILocation{
    final IWorld world;

    protected AbstractLocation(IWorld world) {
        this.world = world;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof AbstractLocation that) {
            return world.equals(that.world) && this.getX() == that.getX() && this.getY() == that.getY() && this.getZ() == that.getZ();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.world, getX(), getY(), getZ());
    }
}
