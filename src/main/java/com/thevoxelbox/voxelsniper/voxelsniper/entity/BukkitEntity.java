package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.BukkitEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.IEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.BukkitVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class BukkitEntity implements IEntity {
    private Entity entity;

    public BukkitEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public IEntityType getType() {
        return new BukkitEntityType(entity.getType());
    }

    @Override
    public void remove() {

    }

    @Override
    public int getEntityId() {
        return entity.getEntityId();
    }

    @Override
    public ILocation getLocation() {
        return new BukkitLocation(entity.getLocation());
    }

    @Override
    public void setVelocity(IVector velocity) {
        entity.setVelocity(((BukkitVector)velocity).getVector());
    }
}
