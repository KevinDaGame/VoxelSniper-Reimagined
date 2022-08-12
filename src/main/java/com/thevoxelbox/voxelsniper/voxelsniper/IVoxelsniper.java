package com.thevoxelbox.voxelsniper.voxelsniper;

import com.thevoxelbox.voxelsniper.bukkit.VoxelSniperConfiguration;
import com.thevoxelbox.voxelsniper.voxelsniper.fileHandler.IFileHandler;
import com.thevoxelbox.voxelsniper.voxelsniper.player.AbstractPlayer;

import java.util.UUID;
import java.util.logging.Logger;

public interface IVoxelsniper {
    AbstractPlayer getPlayer(UUID uuid);
    Environment getEnvironment();
    Version getVersion();

    VoxelSniperConfiguration getVoxelSniperConfiguration();

    IFileHandler getFileHandler();

    Logger getLogger();
}