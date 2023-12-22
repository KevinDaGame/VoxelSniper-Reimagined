package com.github.kevindagame.util.schematic

import java.io.File

interface ISchematicReader {
    fun getSchematicFile(name: String): File?
    fun getSchematicFolder(name: String): File?

    fun readSchematicFile(file: File): VoxelSchematic?
    fun readSchematicFolder(folder: File): List<VoxelSchematic>
    fun getPossibleSchematicNames(): List<String>
}
