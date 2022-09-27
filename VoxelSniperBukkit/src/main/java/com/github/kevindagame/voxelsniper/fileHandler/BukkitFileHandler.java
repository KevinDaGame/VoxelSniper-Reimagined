package com.github.kevindagame.voxelsniper.fileHandler;

import com.github.kevindagame.voxelsniper.BukkitVoxelSniper;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class BukkitFileHandler implements IFileHandler {
    private final BukkitVoxelSniper voxelSniper;

    public BukkitFileHandler(BukkitVoxelSniper bukkitVoxelSniper) {
        this.voxelSniper = bukkitVoxelSniper;
    }

    @Override
    public File getDataFile(String path) {
        return new File(voxelSniper.getDataFolder() + path);
    }

    @Override
    public File getDataFolder() {
        return voxelSniper.getDataFolder();
    }

    @Override
    public void saveResource(@NotNull String resourcePath, boolean replace) {
        voxelSniper.saveResource(resourcePath, replace);
    }
}

