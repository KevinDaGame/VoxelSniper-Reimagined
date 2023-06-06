package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler;
import com.github.kevindagame.voxelsniper.fileHandler.VoxelSniperConfiguration;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public interface IVoxelsniper {
    @Nullable
    IPlayer getPlayer(UUID uuid);

    @Nullable
    IPlayer getPlayer(String name);

    Environment getEnvironment();

    Version getVersion();

    VoxelSniperConfiguration getVoxelSniperConfiguration();

    IFileHandler getFileHandler();

    Logger getLogger();

    List<String> getOnlinePlayerNames();

    @Nullable
    VoxelMaterial getMaterial(String namespace, String key);

    List<VoxelMaterial> getMaterials();
}
