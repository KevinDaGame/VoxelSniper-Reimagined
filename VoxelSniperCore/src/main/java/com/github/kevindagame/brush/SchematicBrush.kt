package com.github.kevindagame.brush

import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.Messages
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.util.brushOperation.BlockOperation
import com.github.kevindagame.util.schematic.SchematicReader
import com.github.kevindagame.util.schematic.VoxelSchematic
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

class SchematicBrush : AbstractBrush() {
    private lateinit var schematicName: String
    private lateinit var schematics: List<VoxelSchematic>
    override fun info(vm: VoxelMessage) {
        vm.brushMessage(Messages.ENTITY_BRUSH_MESSAGE)
        vm.size()
    }

    override fun arrow(v: SnipeData) {
        paste(v)
    }

    override fun powder(v: SnipeData) {
        paste(v)
    }

    /**
     * paste a schematic
     * if there is no schematic loaded, then it will send a message to the player
     * if there is only one schematic loaded, then it will paste that schematic
     * if there are multiple schematics loaded, then it will paste a random schematic from the loaded schematics
     */
    fun paste(v: SnipeData) {
        if (!this::schematics.isInitialized || schematics.isEmpty()) {
            v.owner().player.sendMessage("No schematic loaded")
            return
        }

        if (schematics.size == 1) {
            pasteSchematic(schematics[0], v)
            return
        }

        val schematic = schematics.random()
        pasteSchematic(schematic, v)
    }

    private fun pasteSchematic(schematic: VoxelSchematic, v: SnipeData) {
        val blocks = schematic.blocks
        for (block in blocks) {
            val blockLocation = BaseLocation(world, block.x + targetBlock.x, block.y + targetBlock.y, block.z + targetBlock.z)
            println(blockLocation.toString())
            addOperation(BlockOperation(blockLocation, blockLocation.block.blockData, VoxelMaterial.getMaterial(block.blockData.block).createBlockData()))
        }
    }

    override fun parseParameters(triggerHandle: String, params: Array<String>, v: SnipeData) {
        if (params.isNotEmpty()) {
            when (params[0]) {
                "list" -> {
                    v.sendMessage("Schematics: ${SchematicReader.getPossibleNames().joinToString(", ")}")
                }

                "schem" -> {
                    if (params.size > 1) {
                        try {
                            val schematics = SchematicReader.read(params[1])
                            this.schematicName = params[1]
                            this.schematics = schematics
                            if (schematics.size == 1) {
                                v.sendMessage("Loaded schematic ${params[1]}")
                            } else {
                                v.sendMessage("Loaded ${schematics.size} schematics from ${params[1]}")
                            }
                        } catch (e: IllegalArgumentException) {
                            v.sendMessage(e.message)
                        }
                    }
                }

                "rotate" -> {
                    throw IllegalArgumentException("Not implemented yet")
                }

                "flip" -> {
                    throw IllegalArgumentException("Not implemented yet")
                }

                "mode" -> {
                    throw IllegalArgumentException("Not implemented yet")
                }

            }
        }
    }

    override fun registerArguments(): List<String> {
        return listOf("schem", "rotate", "flip", "list", "mode")
    }

    override fun registerArgumentValues(): HashMap<String, List<String>> {
        return hashMapOf(
            "schem" to SchematicReader.getPossibleNames(),
            "rotate" to RotateMode.values().map { it.name.lowercase().replace("degrees_", "") },
            "flip" to FlipMode.values().map { it.name.lowercase() },
            "mode" to PasteMode.values().map { it.name.toLowerCase() },
            "list" to listOf(),
        )
    }
}

private enum class PasteMode {
    FILL,
    FULL,
    REPLACE
}

private enum class FlipMode {
    X,
    Y,
    Z
}

private enum class RotateMode {
    DEGREES_0,
    DEGREES_90,
    DEGREES_180,
    DEGREES_270,
    DEGREES_RANDOM
}