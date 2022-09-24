package com.thevoxelbox.voxelsniper.voxelsniper;

import com.thevoxelbox.voxelsniper.bukkit.VoxelSniperConfiguration;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.IPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.events.IEventManager;
import com.thevoxelbox.voxelsniper.voxelsniper.fileHandler.IFileHandler;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.jetbrains.annotations.Nullable;

public interface IVoxelsniper {
    @Nullable IPlayer getPlayer(UUID uuid);
    @Nullable IPlayer getPlayer(String name);
    Environment getEnvironment();
    Version getVersion();

    VoxelSniperConfiguration getVoxelSniperConfiguration();

    IFileHandler getFileHandler();

    Logger getLogger();

    List<String> getOnlinePlayerNames();

    @Nullable
    IMaterial getMaterial(VoxelMaterial material);

    IEventManager getEventManager();
}
