package com.thevoxelbox.voxelsniper.voxelsniper.player;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import java.util.Set;
import java.util.UUID;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;

import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;

public interface IPlayer extends IEntity, Audience {
    UUID getUniqueId();
    void sendMessage(String message);
    boolean hasPermission(String permissionNode);
    boolean isSneaking();
    String getName();
    void teleport(ILocation location);
    void eject();
    IEntity launchProjectile(Class<? extends Projectile> projectile);
    IBlock getTargetBlock(Set<VoxelMaterial> transparent, int maxDistance);
    VoxelMaterial getItemInHand();

    @Override
    void sendMessage(final @NotNull Identity source, final @NotNull Component message, final @NotNull MessageType type);
}
