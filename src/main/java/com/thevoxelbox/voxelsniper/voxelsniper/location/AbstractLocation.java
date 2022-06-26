package com.thevoxelbox.voxelsniper.voxelsniper.location;

import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

public abstract class AbstractLocation implements ILocation{
    final IWorld world;

    protected AbstractLocation(IWorld world) {
        this.world = world;
    }
}
