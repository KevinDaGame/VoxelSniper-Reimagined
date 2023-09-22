package com.github.kevindagame.voxelsniperforge.fileHandler;

import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler;
import com.github.kevindagame.voxelsniperforge.VoxelSniperForge;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

import net.minecraftforge.fml.loading.FMLPaths;

public class ForgeFileHandler implements IFileHandler {
    private final VoxelSniperForge voxelSniper;

    public ForgeFileHandler(VoxelSniperForge voxelSniper) {
        this.voxelSniper = voxelSniper;
    }

    @Override
    public File getDataFolder() {
        return new File(FMLPaths.GAMEDIR.get().resolve("config").toFile(), VoxelSniperForge.MODID);
    }

    @Override
    public void saveResource(@NotNull ClassLoader loader, @NotNull String resourcePath, boolean replace) {
        if (resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be empty");
        }
        resourcePath = resourcePath.replace('\\', '/');

        File outFile = new File(getDataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(getDataFolder(), resourcePath.substring(0, Math.max(0, lastIndex)));

        if (!outDir.exists()) {
            if (!outDir.mkdirs())
                voxelSniper.getLogger().log(Level.WARNING, "Could not create directories for " + outDir.getName());
        }

        try (InputStream in = loader.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");
            }
            if (!outFile.exists() || replace) {
                Files.copy(in, outFile.getAbsoluteFile().toPath());
            }
        } catch (IOException ex) {
            voxelSniper.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }
}

