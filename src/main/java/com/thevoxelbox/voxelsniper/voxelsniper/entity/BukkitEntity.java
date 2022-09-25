package com.thevoxelbox.voxelsniper.voxelsniper.entity;

import com.thevoxelbox.voxelsniper.bukkit.BukkitVoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.Painting.BukkitPainting;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

import java.util.List;

public class BukkitEntity implements IEntity {
    private final Entity entity;

    protected BukkitEntity(Entity entity) {
        this.entity = entity;
    }

    public static IEntity fromBukkitEntity(Entity entity) {
        if (entity instanceof Painting p)
            return new BukkitPainting(p);
        if (entity instanceof Player p)
            return BukkitVoxelSniper.getInstance().getPlayer(p);
        return new BukkitEntity(entity);
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
    public IWorld getWorld() {
        return BukkitVoxelSniper.getInstance().getWorld(this.entity.getWorld());
    }

    @Override
    public void addPassenger(IEntity entity) {
        this.entity.addPassenger(((BukkitEntity) entity).getEntity());
    }

    @Override
    public List<IEntity> getPassengers() {
        return this.entity.getPassengers().stream().map(BukkitEntity::fromBukkitEntity).toList();
    }

    @Override
    public boolean teleport(IEntity other) {
        return this.entity.teleport(((BukkitEntity)other).getEntity());
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
        return this.entity.getNearbyEntities(x, y, z).stream().map(e -> (IEntity) BukkitEntity.fromBukkitEntity(e)).toList();
    }

    @Override
    public void eject() {
        entity.eject();
    }
}
