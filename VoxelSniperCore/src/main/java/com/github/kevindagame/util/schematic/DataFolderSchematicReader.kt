package com.github.kevindagame.util.schematic

import com.github.kevindagame.VoxelSniper
import net.sandrohc.schematic4j.SchematicFormat
import net.sandrohc.schematic4j.SchematicLoader
import net.sandrohc.schematic4j.schematic.Schematic
import java.io.File
import java.util.*

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
        val schematic: Schematic
        try {
            schematic = SchematicLoader.load(file)
        } catch (e: Exception) {
            throw IllegalArgumentException("Schematic ${file.name} is not valid")
        }

        val voxelSchematicBuilder = VoxelSchematicBuilder()

        voxelSchematicBuilder.name = file.nameWithoutExtension

        schematic.blocks().forEach { block ->
            voxelSchematicBuilder.addBlock(
                block.left.x.toDouble(),
                block.left.y.toDouble(),
                block.left.z.toDouble(),
                block.right
            )
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
                        if (file.listFiles()?.any { SCHEMATIC_VALID_EXTENSIONS.contains(it.extension) } == true) {
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

        val SCHEMATIC_VALID_EXTENSIONS: List<String> = Arrays.stream(SchematicFormat.values())
            .map { f: SchematicFormat -> f.fileExtension }
            .distinct()
            .toList()

        fun initialize() {
            val schematicsFolder = VoxelSniper.voxelsniper.fileHandler.getDataFile("schematics")
            if (!schematicsFolder.exists()) {
                schematicsFolder.mkdir()
            }
        }
    }
}