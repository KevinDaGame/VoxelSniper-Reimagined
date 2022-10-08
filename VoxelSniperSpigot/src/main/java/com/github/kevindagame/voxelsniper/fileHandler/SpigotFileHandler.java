package com.github.kevindagame.voxelsniper.fileHandler;

import com.github.kevindagame.voxelsniper.SpigotVoxelSniper;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SpigotFileHandler implements IFileHandler {
    private final SpigotVoxelSniper voxelSniper;

    public SpigotFileHandler(SpigotVoxelSniper spigotVoxelSniper) {
        this.voxelSniper = spigotVoxelSniper;
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

