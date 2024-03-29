package com.github.kevindagame.voxelsniper.entity.player;

import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.SpigotVoxelSniper;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.SpigotEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.location.SpigotLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class SpigotPlayer extends SpigotEntity implements IPlayer {
    private final Player player;
    private final @NotNull Sniper sniper;

    public SpigotPlayer(Player player) {
        super(player);
        this.player = player;
        this.sniper = new Sniper(this);
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
    public void teleport(BaseLocation location) {
        player.teleport(SpigotLocation.toSpigotLocation(location));
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
            return SpigotEntity.fromSpigotEntity(player.launchProjectile(projectile, vector));
        }
        return null;
    }

    @Override
    public VoxelMaterial getItemInHand() {
        var item = player.getInventory().getItemInMainHand();
        VoxelMaterial mat = VoxelMaterial.getMaterial(item.getType().getKey().getKey());
        return mat != null ? mat : VoxelMaterial.AIR();
    }

    @Override
    @NotNull
    public Sniper getSniper() {
        return this.sniper;
    }

    @Override
    public void sendMessage(final @NotNull Component message) {
        SpigotVoxelSniper.getAdventure().player(this.player).sendMessage(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpigotPlayer that = (SpigotPlayer) o;
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
