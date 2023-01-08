package com.github.kevindagame.voxelsniper.entity;

import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.List;

public interface IEntity {
    VoxelEntityType getType();

    void remove();

    int getEntityId();

    BaseLocation getLocation();

    IWorld getWorld();

    void addPassenger(IEntity entity);

    List<IEntity> getPassengers();

    BaseLocation getEyeLocation();

    List<IEntity> getNearbyEntities(int x, int y, int z);

    void eject();

    boolean teleport(IEntity other);
}
