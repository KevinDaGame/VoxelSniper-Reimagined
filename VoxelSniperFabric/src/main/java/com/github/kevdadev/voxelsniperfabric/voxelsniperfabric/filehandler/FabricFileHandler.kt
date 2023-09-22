package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.filehandler

import com.github.kevindagame.VoxelSniper
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.logging.Level
import kotlin.math.max

class FabricFileHandler : IFileHandler {
    override fun getDataFolder(): File {
        return File("config/voxelsniper")
    }

    override fun saveResource(loader: ClassLoader, resourcePath: String, replace: Boolean) {
        require(resourcePath != "") { "ResourcePath cannot be empty" }

        val normalizedResourcePath = resourcePath.replace('\\', '/')

        val outFile = File(dataFolder, normalizedResourcePath)
        val lastIndex = normalizedResourcePath.lastIndexOf('/')
        val outDir = File(dataFolder, normalizedResourcePath.substring(0, max(0, lastIndex)))

        if (!outDir.exists()) {
            if (!outDir.mkdirs()) VoxelSniper.voxelsniper.logger
                .log(Level.WARNING, "Could not create directories for " + outDir.name)
        }

        try {
            loader.getResourceAsStream(normalizedResourcePath).use { `in` ->
                requireNotNull(`in`) { "The embedded resource '$normalizedResourcePath' cannot be found" }
                if (!outFile.exists() || replace) {
                    Files.copy(`in`, outFile.absoluteFile.toPath())
                }
            }
        } catch (ex: IOException) {
            VoxelSniper.voxelsniper.logger.log(Level.SEVERE, "Could not save " + outFile.name + " to " + outFile, ex)
        }
    }
}