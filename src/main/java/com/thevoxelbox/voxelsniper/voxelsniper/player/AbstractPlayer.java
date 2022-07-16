package com.thevoxelbox.voxelsniper.voxelsniper.player;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.entity.Projectile;

import java.util.Set;
import java.util.UUID;

public abstract class AbstractPlayer implements IEntity{
    abstract public UUID getUniqueId();
    abstract public void sendMessage(String message);
    abstract public boolean hasPermission(String permissionNode);
    abstract public boolean isSneaking();
    abstract public String getName();
    abstract public void teleport(ILocation location);
    abstract public void eject();
    abstract public IEntity launchProjectile(Class<? extends Projectile> projectile);
    abstract public IBlock getTargetBlock(Set<VoxelMaterial> transparent, int maxDistance);
    abstract public VoxelMaterial getItemInHand();
}
