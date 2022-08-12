package com.thevoxelbox.voxelsniper.voxelsniper.fileHandler;

import java.io.File;

import org.jetbrains.annotations.NotNull;

public interface IFileHandler {
    File getDataFile(String path);
    File getDataFolder();


    void saveResource(@NotNull String resourcePath, boolean replace);
}
