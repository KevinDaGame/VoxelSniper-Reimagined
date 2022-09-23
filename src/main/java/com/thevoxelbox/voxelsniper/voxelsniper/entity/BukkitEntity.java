package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.bukkit.BukkitVoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.Painting.BukkitPainting;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VoxelVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
    public VoxelLocation getLocation() {
        Location loc = entity.getLocation();
        return new VoxelLocation(getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    @Override
    public void setVelocity(VoxelVector velocity) {
        entity.setVelocity(new Vector(velocity.getX(), velocity.getY(), velocity.getZ()));
    }

    @Override
    public IWorld getWorld() {
        return BukkitVoxelSniper.getInstance().getWorld(this.entity.getWorld());
    }

    @Override
    public void addPassenger(IEntity entity) {
        this.entity.addPassenger(((BukkitEntity)entity).getEntity());
    }

    @Override
    public boolean teleport(IEntity player) {
        return this.entity.teleport(((BukkitEntity)entity).getEntity());
    }

    @Override
    public VoxelLocation getEyeLocation() {
        if (entity instanceof LivingEntity living) {
            Location loc = living.getEyeLocation();
            return new VoxelLocation(getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        }
        return this.getLocation();
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
            return BukkitVoxelSniper.getInstance().getPlayer(p);
        return new BukkitEntity(entity);
    }
}
