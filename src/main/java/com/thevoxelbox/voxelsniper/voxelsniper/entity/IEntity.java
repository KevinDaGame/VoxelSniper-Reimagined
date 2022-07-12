package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.IEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public interface IEntity {
    IEntityType getType();

    void remove();

    int getEntityId();

    ILocation getLocation();
    void setVelocity(IVector velocity);

    void setFireTicks(int ticks);

    void removePotionEffect(PotionEffectType effect);

    IWorld getWorld();

    void addPotionEffect(PotionEffect potionEffect, boolean force);
}
