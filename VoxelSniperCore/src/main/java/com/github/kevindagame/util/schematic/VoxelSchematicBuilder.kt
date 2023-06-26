package com.github.kevindagame.util.schematic

import net.sandrohc.schematic4j.schematic.types.SchematicBlock

class VoxelSchematicBuilder {

    val blocks = mutableListOf<VoxelSchematicBlock>()
    var name = ""

    fun addBlock(x: Double, y: Double, z: Double, blockData: SchematicBlock) {
        blocks.add(VoxelSchematicBlock(x, y, z, blockData))
    }
    fun build(): VoxelSchematic {
        return VoxelSchematic(blocks, name)
    }
}

data class VoxelSchematicBlock(var x: Double, var y: Double, var z: Double, val blockData: SchematicBlock)