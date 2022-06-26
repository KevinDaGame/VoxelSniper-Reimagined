package com.thevoxelbox.voxelsniper.voxelsniper.player;

import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitPlayer implements IPlayer{
    private final Player player;

    public BukkitPlayer(Player player) {
        this.player = player;
    }

    @Override
    public BukkitWorld getWorld() {
        return new BukkitWorld(player.getWorld());
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
    public BukkitLocation getLocation() {
        return new BukkitLocation(player.getLocation());
    }

    @Override
    public boolean hasPermission(String permissionNode) {
        return player.hasPermission(permissionNode);
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }
}
