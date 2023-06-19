package com.github.kevindagame.brush

import com.github.kevindagame.VoxelSniper
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.Messages
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.util.brushOperation.BlockOperation
import com.github.kevindagame.util.schematic.SchematicReader
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

class TestBrush: AbstractBrush() {
    override fun info(vm: VoxelMessage) {
        vm.brushMessage(Messages.ENTITY_BRUSH_MESSAGE)
        vm.size()
    }

    override fun arrow(v: SnipeData) {
        val reader = SchematicReader()
        val file = VoxelSniper.voxelsniper.fileHandler.getDataFile("stencils/mountain.schem")
        println(file.absolutePath)
        val schematic = reader.readSchematic(file)

        for (block in schematic.blocks) {
            val material = VoxelMaterial.getMaterial(block.blockData.block)
            val location = BaseLocation(world, block.x + targetBlock.x, block.y + targetBlock.y, block.z + targetBlock.z)
            val operation = BlockOperation(location, world.getBlock(location).blockData, material.createBlockData())
            addOperation(operation)
        }
    }

    override fun powder(v: SnipeData) {
        super.powder(v)
    }

    override fun parseParameters(triggerHandle: String, params: Array<String>, v: SnipeData) {
        super.parseParameters(triggerHandle, params, v)
    }

    override fun registerArguments(): List<String> {
        return super.registerArguments()
    }

    override fun registerArgumentValues(): HashMap<String, List<String>> {
        return super.registerArgumentValues()
    }
}