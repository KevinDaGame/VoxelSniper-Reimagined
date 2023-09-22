package com.github.kevdadev.voxelsniperfabric;

import com.github.kevindagame.voxelsniper.Environment;
import com.github.kevindagame.voxelsniper.IVoxelsniper;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler;
import com.github.kevindagame.voxelsniper.fileHandler.VoxelSniperConfiguration;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class VoxelSniperFabric implements ModInitializer, IVoxelsniper {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {

    }

    @Nullable
    @Override
    public IPlayer getPlayer(UUID uuid) {
        return null;
    }

    @Nullable
    @Override
    public IPlayer getPlayer(String name) {
        return null;
    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    @Override
    public VoxelSniperConfiguration getVoxelSniperConfiguration() {
        return null;
    }

    @Override
    public IFileHandler getFileHandler() {
        return null;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public List<String> getOnlinePlayerNames() {
        return null;
    }

    @Nullable
    @Override
    public VoxelMaterial getMaterial(String namespace, String key) {
        return null;
    }

    @Override
    public List<VoxelMaterial> getMaterials() {
        return null;
    }

    @Nullable
    @Override
    public VoxelBiome getBiome(String namespace, String key) {
        return null;
    }

    @Override
    public List<VoxelBiome> getBiomes() {
        return null;
    }

    @Nullable
    @Override
    public VoxelEntityType getEntityType(String namespace, String key) {
        return null;
    }

    @Override
    public List<VoxelEntityType> getEntityTypes() {
        return null;
    }

    @Nullable
    @Override
    public VoxelTreeType getTreeType(String namespace, String key) {
        return null;
    }

    @NotNull
    @Override
    public VoxelTreeType getDefaultTreeType() {
        return null;
    }

    @Override
    public List<VoxelTreeType> getTreeTypes() {
        return null;
    }
}
