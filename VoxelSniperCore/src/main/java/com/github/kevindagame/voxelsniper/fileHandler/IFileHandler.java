package com.github.kevindagame.voxelsniper.fileHandler;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface IFileHandler {
    default File getDataFile(String path) {
        return new File(getDataFolder(), path);
    }

    File getDataFolder();


    void saveResource(@NotNull ClassLoader loader, @NotNull String resourcePath, boolean replace);
}
