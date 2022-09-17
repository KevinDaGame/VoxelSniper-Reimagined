package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VoxelVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.List;

public interface IEntity {
    VoxelEntityType getType();

    void remove();

    int getEntityId();

    ILocation getLocation();
    void setVelocity(VoxelVector velocity);
    IWorld getWorld();

    void addPassenger(IEntity entity);
    ILocation getEyeLocation();

    List<IEntity> getNearbyEntities(int x, int y, int z);

    void eject();
}
