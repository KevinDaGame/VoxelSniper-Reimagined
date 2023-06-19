package com.github.kevindagame.util.schematic

import net.sandrohc.schematic4j.SchematicUtil
import net.sandrohc.schematic4j.schematic.types.SchematicPosInteger
import java.io.File

class SchematicReader {
    fun readSchematic(file: File): VoxelSchematic {
        val schematic = SchematicUtil.load(file)
        val name = schematic.name
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
}