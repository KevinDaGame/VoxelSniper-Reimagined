package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler;
import com.github.kevindagame.voxelsniper.fileHandler.VoxelSniperConfiguration;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType;
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

    VoxelSniperConfiguration getVoxelSniperConfiguration();

    IFileHandler getFileHandler();

    Logger getLogger();

    List<String> getOnlinePlayerNames();

    @Nullable
    VoxelMaterial getMaterial(String namespace, String key);

    List<VoxelMaterial> getMaterials();

    @Nullable
    VoxelBiome getBiome(String namespace, String key);

    List<VoxelBiome> getBiomes();

    @Nullable
    VoxelEntityType getEntityType(String namespace, String key);

    List<VoxelEntityType> getEntityTypes();

    @Nullable
    VoxelTreeType getTreeType(String namespace, String key);

    List<VoxelTreeType> getTreeTypes();
}
