package com.github.kevindagame.util.schematic

import com.github.kevindagame.VoxelSniper
import net.sandrohc.schematic4j.SchematicLoader
import java.io.File

class DataFolderSchematicReader : ISchematicReader {
    override fun getSchematicFile(name: String): File? {
        //check for exact name
        val exactSchematic = VoxelSniper.voxelsniper.fileHandler.getDataFile(SCHEMATIC_FILE_ROOT_PATH + name)
        if (exactSchematic.isFile) {
            return exactSchematic
        }

        //if no, check for name with any extension
        val schematicFiles = VoxelSniper.voxelsniper.fileHandler.getDataFile(SCHEMATIC_FILE_ROOT_PATH).listFiles()
        if (schematicFiles != null) {
            for (schematic in schematicFiles) {
                if (schematic.isFile) {
                    if (schematic.nameWithoutExtension == name) {
                        return schematic
                    }
                }
            }
        }

        return null
    }

    override fun getSchematicFolder(name: String): File? {
        val path = SCHEMATIC_FILE_ROOT_PATH + name
        val schematicFolder = VoxelSniper.voxelsniper.fileHandler.getDataFile(path)
        if (schematicFolder.isDirectory) {
            return schematicFolder
        }
        return null
    }

    override fun readSchematicFile(file: File): VoxelSchematic {
        val schematic = SchematicLoader.load(file)
        val voxelSchematicBuilder = VoxelSchematicBuilder()

        voxelSchematicBuilder.name = file.nameWithoutExtension

        for (y in 0 until schematic.height()) {
            for (x in 0 until schematic.width()) {
                for (z in 0 until schematic.length()) {

                    val block = schematic.block(x, y, z)
                    voxelSchematicBuilder.addBlock(x.toDouble(), y.toDouble(), z.toDouble(), block)
                }
            }
        }
        return voxelSchematicBuilder.build()
    }

    override fun readSchematicFolder(folder: File): List<VoxelSchematic> {
        val schematics = mutableListOf<VoxelSchematic>()

        val schematicFiles = folder.listFiles() ?: throw IllegalStateException("Folder $folder does not exist")

        for (schematicFile in schematicFiles) {
            if (schematicFile.isFile) {

                schematics.add(readSchematicFile(schematicFile))
            }

        }
        return schematics
    }

    override fun getPossibleSchematicNames(): List<String> {
        val schematics = mutableListOf<String>()
        val schematicsFolder = VoxelSniper.voxelsniper.fileHandler.getDataFile(SCHEMATIC_FILE_ROOT_PATH)
        if (schematicsFolder.exists()) {
            val files = schematicsFolder.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isFile) {
                        schematics.add(file.nameWithoutExtension)
                    } else if (file.isDirectory) {
                        if (file.listFiles()?.any { it.extension == "schem" } == true) {
                            schematics.add(file.name)
                        }
                    }
                }
            }
        }
        return schematics
    }

    companion object {
        const val SCHEMATIC_FILE_ROOT_PATH = "schematics/"

        fun initialize() {
            val schematicsFolder = VoxelSniper.voxelsniper.fileHandler.getDataFile("schematics")
            if (!schematicsFolder.exists()) {
                schematicsFolder.mkdir()
            }
        }
    }
}