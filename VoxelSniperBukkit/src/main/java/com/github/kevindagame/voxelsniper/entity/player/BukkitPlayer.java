package com.github.kevindagame.voxelsniper.entity.player;

import com.github.kevindagame.voxelsniper.BukkitVoxelSniper;
import com.github.kevindagame.voxelsniper.block.BukkitBlock;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.BukkitEntity;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BukkitLocation;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.BukkitMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BukkitPlayer extends BukkitEntity implements IPlayer {
    private final Player player;

    public BukkitPlayer(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permissionNode) {
        return player.hasPermission(permissionNode);
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void teleport(VoxelLocation location) {
        player.teleport(BukkitLocation.toBukkitLocation(location));
    }

    @Override
    public void remove() {

    }

    @Override
    public IEntity launchProjectile(VoxelEntityType type, VoxelVector velocity) {
        Class<? extends Entity> clazz = EntityType.valueOf(type.getKey().toUpperCase(Locale.ROOT)).getEntityClass();
        if (clazz != null && Projectile.class.isAssignableFrom(clazz)) {
            Class<? extends Projectile> projectile = clazz.asSubclass(Projectile.class);
            Vector vector = new Vector(velocity.getX(), velocity.getY(), velocity.getZ());
            return BukkitEntity.fromBukkitEntity(player.launchProjectile(projectile, vector));
        }
        return null;
    }

    @Override
    public IBlock getTargetBlock(Set<VoxelMaterial> transparent, int maxDistance) {
        Set<Material> materials = transparent != null ? transparent.stream().map(m -> ((BukkitMaterial) m.getIMaterial()).material()).collect(Collectors.toSet()) : null;
        return new BukkitBlock(player.getTargetBlock(materials, maxDistance));
    }

    @Override
    public VoxelMaterial getItemInHand() {
        var item = player.getInventory().getItemInMainHand();
        VoxelMaterial mat = VoxelMaterial.getMaterial(item.getType().getKey().getKey());
        return mat != null ? mat : VoxelMaterial.AIR;
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        BukkitVoxelSniper.getAdventure().player(this.player).sendMessage(source, message, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BukkitPlayer that = (BukkitPlayer) o;
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
