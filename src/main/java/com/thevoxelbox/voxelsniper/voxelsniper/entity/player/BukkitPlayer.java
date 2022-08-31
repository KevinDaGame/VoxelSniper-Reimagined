package com.thevoxelbox.voxelsniper.voxelsniper.entity.player;

import com.thevoxelbox.voxelsniper.bukkit.BukkitVoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.BukkitEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;

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
    public void setVelocity(IVector velocity) {

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
    public void teleport(ILocation location) {
        player.teleport(((BukkitLocation) location).getLocation());
    }

    @Override
    public void remove() {

    }

    @Override
    public IEntity launchProjectile(Class<? extends Projectile> fireball) {
        return BukkitEntity.fromBukkitEntity(player.launchProjectile(fireball));
    }

    @Override
    public IBlock getTargetBlock(Set<VoxelMaterial> transparent, int maxDistance) {
        Set<Material> materials = transparent.stream().map(m -> ((BukkitMaterial)m.getIMaterial()).material()).collect(Collectors.toSet());
        return new BukkitBlock(player.getTargetBlock(materials, maxDistance));
    }

    @Override
    public VoxelMaterial getItemInHand() {
        var item = player.getInventory().getItemInMainHand();
        return new VoxelMaterial(item.getType().getKey().getKey());
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
