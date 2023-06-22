package com.github.kevindagame.util.schematic

import com.github.kevindagame.VoxelSniper
import net.sandrohc.schematic4j.SchematicUtil
import net.sandrohc.schematic4j.schematic.types.SchematicPosInteger
import java.io.File

object SchematicReader {

    private fun readSchematic(file: File): VoxelSchematic {
        val schematic = SchematicUtil.load(file)
        val voxelSchematicBuilder = VoxelSchematicBuilder()

        for (y in 0 until schematic.height) {
            for (x in 0 until schematic.width) {
                for (z in 0 until schematic.length) {
                    val pos = SchematicPosInteger(x, y, z)
                    val block = schematic.getBlock(pos)
                    voxelSchematicBuilder.addBlock(x.toDouble(), y.toDouble(), z.toDouble(), block)
                }
            }
        }
        return voxelSchematicBuilder.build()
    }

    private fun readSchematics(folder: File): MutableList<VoxelSchematic> {
        if (!folder.exists()) {
            throw IllegalArgumentException("Folder $folder does not exist")
        }
        val schematicFiles = folder.listFiles()
        if (schematicFiles != null) {
            if(schematicFiles.none { it.extension == "schem" }) {
                throw IllegalArgumentException("Folder $folder does not contain any schematics")
            }
        }
        val schematics = mutableListOf<VoxelSchematic>()
        if (schematicFiles != null) {
            for (schematic in schematicFiles) {
                if (schematic.isFile) {
                    if (schematic.extension != "schem") {
                        continue
                    }
                    schematics.add(readSchematic(schematic))
                }
            }
        }
        return schematics
    }

    /**
     * Reads a schematic from a file or a folder.
     * If the name ends with .schem, then it will read the file.
     *     If there is no file with the name.schem, then it will throw an IllegalArgumentException.
     * If the name does not end with .schem, then it will check if there is a folder with that name.
     *     If there is a folder with that name, then it will read all schematics in that folder.
     *     If there are no schematics in that folder, then it will throw an IllegalArgumentException.
     * If there is no folder with that name, then it will check if there is a file with the name.schem.
     * If there is a file with the name.schem, then it will read the file.
     * If there is no file with the name.schem, then it will throw an IllegalArgumentException.
     * @param name The name of the schematic or the folder.
     * @return A list of schematics.
     * @throws IllegalArgumentException If there is no file with the name.schem or no folder with that name.
     */
    fun read(name: String): List<VoxelSchematic> {

        //if name ends with .schem, then check if it exists. if yes, then call readFile with the file.
        if (name.endsWith(".schem")) {
            val file = VoxelSniper.voxelsniper.fileHandler.getDataFile("stencils/$name")
            if (!file.exists()) {
                throw IllegalArgumentException("File $name does not exist")
            }
            return listOf(readSchematic(file))
        }

        // If name does not end with .schem, then check if there is a folder with that name. If yes, then call readFolder with the folder.
        val folder = VoxelSniper.voxelsniper.fileHandler.getDataFile("stencils/$name")
        if (folder.exists()) {
            return readSchematics(folder)
        }

        // If no, check if there is a file with the name.schem. If yes, then call readFile with the file.
        val file = VoxelSniper.voxelsniper.fileHandler.getDataFile("stencils/$name.schem")
        if (file.exists()) {
            return listOf(readSchematic(file))
        }

        // If no, then throw an exception.
        throw IllegalArgumentException("File $name does not exist")

    }

    fun getPossibleNames(): List<String> {
        val schematicsFolder = VoxelSniper.voxelsniper.fileHandler.getDataFile("stencils")
        val schematics = mutableListOf<String>()
        if (schematicsFolder.exists()) {
            val files = schematicsFolder.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isFile) {
                        if (file.extension == "schem") {
                            schematics.add(file.nameWithoutExtension)
                        }
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
}