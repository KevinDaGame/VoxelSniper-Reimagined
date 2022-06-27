package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.IEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import org.bukkit.Location;

public interface IEntity {
    IEntityType getType();

    void remove();

    int getEntityId();

    ILocation getLocation();
}
