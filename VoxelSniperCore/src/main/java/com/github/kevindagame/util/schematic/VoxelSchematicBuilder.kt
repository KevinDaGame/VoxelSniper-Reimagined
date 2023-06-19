package com.github.kevindagame.util.schematic

import net.sandrohc.schematic4j.schematic.types.SchematicBlock

class VoxelSchematicBuilder {

    val blocks = mutableListOf<VoxelSchematicBlock>()

    fun addBlock(x: Double, y: Double, z: Double, blockData: SchematicBlock) {
        blocks.add(VoxelSchematicBlock(x, y, z, blockData))
    }
    fun build(): VoxelSchematic {
        return VoxelSchematic(blocks)
    }
}

data class VoxelSchematicBlock(val x: Double, val y: Double, val z: Double, val blockData: SchematicBlock)