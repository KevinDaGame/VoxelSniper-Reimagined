package com.github.kevindagame.voxelsniperforge.entity.player;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.entity.ForgeEntity;
import com.github.kevindagame.voxelsniperforge.location.ForgeLocation;
import com.github.kevindagame.voxelsniperforge.material.ForgeMaterial;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        var relativeArguments = java.util.EnumSet.noneOf(net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket.RelativeArgument.class);

        var location = other.getLocation();
        Preconditions.checkArgument(location.getWorld() != null, "location.world");

        if (this.player.getHealth() == 0 || this.player.isRemoved()) {
            return false;
        }

        if (this.player.connection == null) {
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
        ServerLevel fromWorld = this.player.getLevel();
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
    public IBlock getTargetBlock(Set<VoxelMaterial> transparent, int maxDistance) {
        Set<Material> materials = transparent != null ? transparent.stream().map(m -> ((ForgeMaterial) m.getIMaterial()).material()).collect(Collectors.toSet()) : null;

        return new ForgeBlock(player.getTargetBlock(materials, maxDistance));
    }

    @Override
    public VoxelMaterial getItemInHand() {
        var inv = player.getInventory();
        var item = inv.getItem(inv.selected);
        VoxelMaterial mat = VoxelMaterial.getMaterial(item.getItem());
        return mat != null ? mat : VoxelMaterial.AIR;
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
