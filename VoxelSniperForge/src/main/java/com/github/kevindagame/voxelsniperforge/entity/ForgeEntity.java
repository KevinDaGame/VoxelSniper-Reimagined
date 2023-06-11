package com.github.kevindagame.voxelsniperforge.entity;

import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.world.IWorld;
import com.github.kevindagame.voxelsniperforge.VoxelSniperForge;
import com.github.kevindagame.voxelsniperforge.entity.Painting.ForgePainting;
import com.github.kevindagame.voxelsniperforge.world.ForgeWorld;

import java.util.List;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;

public class ForgeEntity implements IEntity {
    private final Entity entity;

    protected ForgeEntity(Entity entity) {
        this.entity = entity;
    }

    public static IEntity fromForgeEntity(Entity entity) {
        if (entity instanceof Painting p)
            return new ForgePainting(p);
        if (entity instanceof ServerPlayer p)
            return VoxelSniperForge.getInstance().getPlayer(p);
        return new ForgeEntity(entity);
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public VoxelEntityType getType() {
        var type = EntityType.getKey(entity.getType());
        return VoxelEntityType.getEntityType(type.getNamespace(), type.getPath());
    }

    @Override
    public void remove() {
        entity.remove(Entity.RemovalReason.DISCARDED);
    }

    @Override
    public int getEntityId() {
        return entity.getId();
    }

    @Override
    public BaseLocation getLocation() {
        return new BaseLocation(getWorld(), entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
    }

    @Override
    public IWorld getWorld() {
        return new ForgeWorld((ServerLevel) this.entity.level());
    }

    @Override
    public void addPassenger(IEntity entity) {
        ((ForgeEntity) entity).getEntity().startRiding(this.entity, true);
    }

    @Override
    public List<IEntity> getPassengers() {
        return this.entity.getPassengers().stream().map(ForgeEntity::fromForgeEntity).toList();
    }

    @Override
    public boolean teleport(IEntity other) {
        var location = other.getLocation();

        if (this.entity.isVehicle() || this.entity.isRemoved()) {
            return false;
        }

        // If this entity is riding another entity, we must dismount before teleporting.
        this.entity.stopRiding();

        entity.moveTo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch()); // Paper - use proper moveTo, as per vanilla teleporting
        this.entity.setYHeadRot(location.getYaw());
        return true;
    }

    @Override
    public BaseLocation getEyeLocation() {
        return new BaseLocation(getWorld(), entity.getX(), entity.getEyeY(), entity.getZ(), entity.getYRot(), entity.getXRot());
    }

    @Override
    public List<IEntity> getNearbyEntities(int x, int y, int z) {
        List<Entity> notchEntityList = entity.level().getEntities(entity, this.entity.getBoundingBox().inflate(x, y, z), entity1 -> true);
        return notchEntityList.stream().map(ForgeEntity::fromForgeEntity).toList();
    }

    @Override
    public void eject() {
        entity.ejectPassengers();
    }
}
