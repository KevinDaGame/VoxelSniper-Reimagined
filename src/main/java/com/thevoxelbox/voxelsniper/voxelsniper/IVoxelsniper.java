package com.thevoxelbox.voxelsniper.voxelsniper;

import com.thevoxelbox.voxelsniper.bukkit.VoxelSniperConfiguration;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.IPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.fileHandler.IFileHandler;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface IVoxelsniper {
    @Nullable IPlayer getPlayer(UUID uuid);
    @Nullable IPlayer getPlayer(String name);
    Environment getEnvironment();
    Version getVersion();

    VoxelSniperConfiguration getVoxelSniperConfiguration();

    IFileHandler getFileHandler();

    Logger getLogger();

    List<String> getOnlinePlayerNames();
}
