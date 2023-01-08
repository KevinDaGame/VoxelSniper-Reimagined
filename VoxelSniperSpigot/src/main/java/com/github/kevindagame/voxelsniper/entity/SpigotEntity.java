package com.github.kevindagame.voxelsniper.entity;

import com.github.kevindagame.voxelsniper.SpigotVoxelSniper;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.entity.painting.SpigotPainting;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.world.IWorld;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

import java.util.List;

public class SpigotEntity implements IEntity {
    private final Entity entity;

    protected SpigotEntity(Entity entity) {
        this.entity = entity;
    }

    public static IEntity fromSpigotEntity(Entity entity) {
        if (entity instanceof Painting p)
            return new SpigotPainting(p);
        if (entity instanceof Player p)
            return SpigotVoxelSniper.getInstance().getPlayer(p);
        return new SpigotEntity(entity);
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
    public BaseLocation getLocation() {
        Location loc = entity.getLocation();
        return new BaseLocation(getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    @Override
    public IWorld getWorld() {
        return SpigotVoxelSniper.getInstance().getWorld(this.entity.getWorld());
    }

    @Override
    public void addPassenger(IEntity entity) {
        this.entity.addPassenger(((SpigotEntity) entity).getEntity());
    }

    @Override
    public List<IEntity> getPassengers() {
        return this.entity.getPassengers().stream().map(SpigotEntity::fromSpigotEntity).toList();
    }

    @Override
    public boolean teleport(IEntity other) {
        return this.entity.teleport(((SpigotEntity) other).getEntity());
    }

    @Override
    public BaseLocation getEyeLocation() {
        if (entity instanceof LivingEntity living) {
            Location loc = living.getEyeLocation();
            return new BaseLocation(getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        }
        return this.getLocation();
    }

    @Override
    public List<IEntity> getNearbyEntities(int x, int y, int z) {
        return this.entity.getNearbyEntities(x, y, z).stream().map(SpigotEntity::fromSpigotEntity).toList();
    }

    @Override
    public void eject() {
        entity.eject();
    }
}
