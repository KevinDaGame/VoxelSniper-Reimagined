package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.List;

public interface IEntity {
    VoxelEntityType getType();

    void remove();

    int getEntityId();

    VoxelLocation getLocation();

    IWorld getWorld();

    void addPassenger(IEntity entity);

    VoxelLocation getEyeLocation();

    List<IEntity> getNearbyEntities(int x, int y, int z);

    void eject();

    boolean teleport(IEntity player);
}
