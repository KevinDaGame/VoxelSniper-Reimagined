package com.thevoxelbox.voxelsniper.voxelsniper.entity.player;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.entitytype.VoxelEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VoxelVector;
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

    void teleport(VoxelLocation location);

    IEntity launchProjectile(VoxelEntityType type, VoxelVector velocity);

    IBlock getTargetBlock(Set<VoxelMaterial> transparent, int maxDistance);

    VoxelMaterial getItemInHand();

    @Override
    void sendMessage(final @NotNull Identity source, final @NotNull Component message, final @NotNull MessageType type);
}
