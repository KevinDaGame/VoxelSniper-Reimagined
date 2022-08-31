package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.voxelsniper.entity.Painting.BukkitPainting;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.BukkitPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.BukkitVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

public class BukkitEntity implements IEntity {
    private final Entity entity;

    protected BukkitEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public VoxelEntityType getType() {
        return VoxelEntityType.getEntityType(entity.getType().getKey().getKey());
    }

    @Override
    public void remove() {
        entity.remove();
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
        entity.setVelocity(((BukkitVector)velocity).vector());
    }

    @Override
    public IWorld getWorld() {
        return new BukkitWorld(this.entity.getWorld());
    }

    @Override
    public void addPassenger(IEntity entity) {
        this.entity.addPassenger(((BukkitEntity)entity).getEntity());
    }

    @Override
    public ILocation getEyeLocation() {
        return new BukkitLocation(((LivingEntity)entity).getEyeLocation());
    }

    @Override
    public List<IEntity> getNearbyEntities(int x, int y, int z) {
        return this.entity.getNearbyEntities(x, y, z).stream().map(e -> (IEntity)BukkitEntity.fromBukkitEntity(e)).toList();
    }

    @Override
    public void eject() {
        entity.eject();
    }

    public static BukkitEntity fromBukkitEntity(Entity entity) {
        if (entity instanceof Painting p)
            return new BukkitPainting(p);
        if (entity instanceof Player p)
            return new BukkitPlayer(p);
        return new BukkitEntity(entity);
    }
}
