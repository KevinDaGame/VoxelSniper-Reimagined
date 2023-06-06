package com.github.kevindagame.voxelsniper.entity.player;

import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public interface IPlayer extends IEntity {
    UUID getUniqueId();

    void sendMessage(String message);

    void sendMessage(final @NotNull Component message);

    default void sendMessage(final @NotNull ComponentLike message) { sendMessage(message.asComponent()); }

    boolean hasPermission(String permissionNode);

    boolean isSneaking();

    String getName();

    void teleport(BaseLocation location);

    IEntity launchProjectile(VoxelEntityType type, VoxelVector velocity);

    VoxelMaterial getItemInHand();

    @NotNull
    Sniper getSniper();
}
