package com.thevoxelbox.voxelsniper.voxelsniper.player;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LargeFireball;

import java.util.UUID;

public interface IPlayer {
    IWorld getWorld();
    UUID getUniqueId();
    void sendMessage(String message);
    ILocation getLocation();
    boolean hasPermission(String permissionNode);

    boolean isSneaking();

    ILocation getEyeLocation();

    String getName();

    void teleport(ILocation location);

    int getEntityId();

    void eject();

    Entity launchProjectile(Class<Fireball> fireball);
}
