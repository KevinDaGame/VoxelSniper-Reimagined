package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.IEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

public interface IEntity {
    IEntityType getType();

    void remove();

    int getEntityId();

    ILocation getLocation();
    void setVelocity(IVector velocity);
    IWorld getWorld();

    void addPassenger(IEntity entity);
    ILocation getEyeLocation();
}
