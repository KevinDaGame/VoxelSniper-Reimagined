package com.github.kevindagame.voxelsniper.entity.player;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public interface IPlayer extends IEntity, Audience {
    UUID getUniqueId();

    void sendMessage(String message);

    boolean hasPermission(String permissionNode);

    boolean isSneaking();

    String getName();

    void teleport(BaseLocation location);

    IEntity launchProjectile(VoxelEntityType type, VoxelVector velocity);

    IBlock getTargetBlock(Set<VoxelMaterial> transparent, int maxDistance);

    VoxelMaterial getItemInHand();

    @Override
    void sendMessage(final @NotNull Identity source, final @NotNull Component message, final @NotNull MessageType type);
}
