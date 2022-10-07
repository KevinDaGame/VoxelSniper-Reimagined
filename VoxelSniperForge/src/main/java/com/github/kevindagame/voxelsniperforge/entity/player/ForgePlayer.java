package com.github.kevindagame.voxelsniperforge.entity.player;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniperforge.entity.ForgeEntity;
import com.github.kevindagame.voxelsniperforge.location.ForgeLocation;
import com.google.common.base.Preconditions;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class ForgePlayer extends ForgeEntity implements IPlayer {
    private final ServerPlayer player;

    public ForgePlayer(ServerPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public UUID getUniqueId() {
        return player.getUUID();
    }

    @Override
    public void sendMessage(String message) {
        VoxelSniper.voxelsniper.getLogger().warning("player.sendMessage needs implementation");
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        VoxelSniper.voxelsniper.getLogger().warning("player.sendMessage needs implementation");
    }

    @Override
    public boolean hasPermission(String permissionNode) {
        VoxelSniper.voxelsniper.getLogger().warning("player.hasPermission needs implementation");
        return true;
    }

    @Override
    public boolean isSneaking() {
        return player.isCrouching();
    }

    @Override
    public String getName() {
        return player.getName().getString();
    }

    @Override
    public void teleport(VoxelLocation location) {
        player.moveTo(ForgeLocation.toForgeBlockPos(location), location.getYaw(), location.getPitch());
    }

    @Override
    public void remove() {

    }

    @Override
    public boolean teleport(IEntity other) {
        var location = other.getLocation();
        Preconditions.checkArgument(location.getWorld() != null, "location.world");

        if (this.player.getHealth() == 0 || this.player.isRemoved()) {
            return false;
        }

        if (this.player.isVehicle()) {
            return false;
        }

        // If this player is riding another entity, we must dismount before teleporting.
        this.player.stopRiding(); // Paper - Teleport API

        // SPIGOT-5509: Wakeup, similar to riding
        if (this.player.isSleeping()) {
            this.player.stopSleepInBed(true, false);
        }

        // Grab the To and From World Handles.
        ServerLevel toWorld = (ServerLevel) ((ForgeEntity) other).getEntity().getLevel();

        // Close any foreign inventory
        if (this.player.containerMenu != this.player.inventoryMenu) {
            this.player.closeContainer();
        }

        // Check if the fromWorld and toWorld are the same.
        this.player.teleportTo(toWorld, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        return true;
    }

    @Override
    public IEntity launchProjectile(VoxelEntityType type, VoxelVector velocity) {
        var tag = EntityType.byString(type.getKey());
        if (tag.isPresent()){
            Entity created = tag.get().create(player.getLevel());
            if (created instanceof Projectile) {
                created.setPos(player.getX(), player.getY(), player.getZ());
                Vec3 vector = new Vec3(velocity.getX(), velocity.getY(), velocity.getZ());
                created.setDeltaMovement(vector);
                player.getLevel().addFreshEntity(created);
            }
        } else {
            throw new IllegalArgumentException("Invalid entity type");
        }
        return null;
    }

    @Override
    public VoxelMaterial getItemInHand() {
        var itemStack = player.getMainHandItem();
        ResourceLocation item = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        return item != null ? VoxelMaterial.getMaterial(item.getNamespace(), item.getPath()) : VoxelMaterial.AIR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForgePlayer that = (ForgePlayer) o;
        return player.equals(that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    public Player getPlayer() {
        return player;
    }
}