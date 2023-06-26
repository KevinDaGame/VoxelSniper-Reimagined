package com.github.kevindagame.util.schematic

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import net.sandrohc.schematic4j.schematic.types.SchematicBlock
import org.junit.Test

class VoxelSchematicBuilderTest {
    @Test
    fun testAddBlock() {
        val builder = VoxelSchematicBuilder()
        val blockData = SchematicBlock("stone")
        builder.addBlock(0.0, 1.0, 2.0, blockData)
        assertEquals(builder.blocks.size, 1)
        assertEquals(builder.blocks.first(), VoxelSchematicBlock(0.0, 1.0, 2.0, blockData))
    }

    @Test
    fun testBuild() {
        val builder = VoxelSchematicBuilder()
        builder.name = "test schematic"
        val blockData1 = SchematicBlock("stone")
        builder.addBlock(0.0, 1.0, 2.0, blockData1)
        val blockData2 = SchematicBlock("air")
        builder.addBlock(1.0, 0.0, 2.0, blockData2)
        val schematic = builder.build()
        assertEquals(schematic.blocks.size, 2)
        assertTrue(schematic.blocks.contains(VoxelSchematicBlock(0.0, 1.0, 2.0, blockData1)))
        assertTrue(schematic.blocks.contains(VoxelSchematicBlock(1.0, 0.0, 2.0, blockData2)))
        assertEquals(schematic.name, "test schematic")
    }
}