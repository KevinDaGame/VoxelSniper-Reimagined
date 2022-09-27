package com.thevoxelbox.voxelsniper.voxelsniper.fileHandler;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface IFileHandler {
    File getDataFile(String path);

    File getDataFolder();


    void saveResource(@NotNull String resourcePath, boolean replace);
}
