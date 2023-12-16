package com.github.kevindagame.brush

import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.Messages
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.util.brushOperation.BlockOperation
import com.github.kevindagame.util.schematic.*
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

class SchematicBrush : AbstractBrush() {
    private val schematicLoader: VoxelSchematicLoader
    private lateinit var schematicName: String
    private lateinit var schematics: List<VoxelSchematic>
    private var mode = PasteMode.FULL
    private var rotation = RotateMode.DEGREES_0
    private var flip = FlipMode.NONE

    init {
        val schematicReader: ISchematicReader = DataFolderSchematicReader()
        this.schematicLoader = VoxelSchematicLoader(schematicReader)
    }
    override fun info(vm: VoxelMessage) {
        vm.brushName(this.name)
        vm.custom(Messages.CHOSEN_SCHEMATIC.replace("%schematics%", if(this::schematics.isInitialized) schematics.joinToString(", ") { it.name } else "None"))
        vm.custom(Messages.SCHEMATIC_MODE.replace("%mode%", mode.name.lowercase()))
        vm.custom(Messages.SCHEMATIC_ROTATION.replace("%rotation%", rotation.name.lowercase().replace("degrees_", "")))
        vm.custom(Messages.SCHEMATIC_FLIP.replace("%flip%", flip.name.lowercase()))
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
    private fun paste(v: SnipeData) {
        if (!this::schematics.isInitialized || schematics.isEmpty()) {
            v.owner().player.sendMessage("No schematic loaded")
            return
        }

        if (schematics.size == 1) {
            pasteSchematic(schematics[0])
            return
        }

        val schematic = schematics.random()
        pasteSchematic(schematic)
    }

    private fun pasteSchematic(schematic: VoxelSchematic) {
        val blocks = rotateAndFlip(schematic.blocks)

        for (block in blocks) {
            val blockLocation = BaseLocation(world, block.x + targetBlock.x, block.y + targetBlock.y, block.z + targetBlock.z)
            when(this.mode) {
                PasteMode.FILL -> {
                    if (blockLocation.block.blockData.material.isAir) {
                        addOperation(BlockOperation(blockLocation, blockLocation.block.blockData, VoxelMaterial.getMaterial(block.blockData.block).createBlockData()))
                    }
                }

                PasteMode.FULL -> {
                    addOperation(BlockOperation(blockLocation, blockLocation.block.blockData, VoxelMaterial.getMaterial(block.blockData.block).createBlockData()))
                }

                PasteMode.REPLACE -> {
                    if (!blockLocation.block.blockData.material.isAir) {
                        addOperation(BlockOperation(blockLocation, blockLocation.block.blockData, VoxelMaterial.getMaterial(block.blockData.block).createBlockData()))
                    }
                }
            }
        }
    }

    private fun rotateAndFlip(blocks: List<VoxelSchematicBlock>): MutableList<VoxelSchematicBlock> {
        val rotationDegrees = when (this.rotation) {
            RotateMode.DEGREES_0 -> 0
            RotateMode.DEGREES_90 -> 90
            RotateMode.DEGREES_180 -> 180
            RotateMode.DEGREES_270 -> 270
            RotateMode.DEGREES_RANDOM -> (listOf(90, 180, 270, 0)).random()
        }

        val newBlocks = mutableListOf<VoxelSchematicBlock>()

        for (block in blocks) {
            val newBlock = rotateBlock(block, rotationDegrees)
            flipBlock(newBlock)
            newBlocks.add(newBlock)
        }

        return newBlocks
    }

    private fun rotateBlock(
        block: VoxelSchematicBlock,
        rotationDegrees: Int
    ): VoxelSchematicBlock {
        when (rotationDegrees) {
            0 -> {
                return VoxelSchematicBlock(block.x, block.y, block.z, block.blockData)
            }

            90 -> {
                return VoxelSchematicBlock(-block.z, block.y, block.x, block.blockData)
            }

            180 -> {
                return VoxelSchematicBlock(-block.x, block.y, -block.z, block.blockData)
            }

            270 -> {
                return VoxelSchematicBlock(block.z, block.y, -block.x, block.blockData)
            }

        }
        throw IllegalArgumentException("Invalid rotation degrees: $rotationDegrees")
    }

    private fun flipBlock(block: VoxelSchematicBlock) {
            when (this.flip) {
                FlipMode.NONE -> {
                    return
                }

                FlipMode.X -> {
                    block.x = -block.x
                }

                FlipMode.Y -> {
                    block.y = -block.y
                }

                FlipMode.Z -> {
                    block.z = -block.z
                }
        }
    }

    override fun parseParameters(triggerHandle: String, params: Array<String>, v: SnipeData) {
        if (params.isNotEmpty()) {
            when (params[0]) {
                "list" -> {
                    v.sendMessage(Messages.SCHEMATIC_LIST.replace("%schematics%", schematicLoader.getSchematicNamesForAutoComplete().joinToString(", ")))
                }

                "schem" -> {
                    if (params.size > 1) {
                        try {
                            val schematics = schematicLoader.gatherSchematics(params[1])
                            this.schematicName = params[1]
                            this.schematics = schematics
                            if (schematics.size == 1) {
                                v.sendMessage(Messages.SCHEMATIC_LOADED_ONE.replace("%schematic%", this.schematics.first().name))
                            } else {
                                v.sendMessage(Messages.SCHEMATIC_LOADED_MULTIPLE.replace("%schematics%", this.schematics.joinToString(", ") { it.name }))
                            }
                        } catch (e: IllegalArgumentException) {
                            v.sendMessage(Messages.FILE_LOAD_FAIL.replace("%exception.getMessage%", e.message ?: "Unknown error"))
                        }
                    }
                }

                "rotate" -> {
                    if (params.size > 1) {
                        try {
                            this.rotation = RotateMode.valueOf("DEGREES_${params[1].uppercase()}")
                            v.sendMessage(Messages.SCHEMATIC_SET_ROTATION.replace("%rotation%", this.rotation.name.lowercase().replace("degrees_", "")))
                        } catch (e: IllegalArgumentException) {
                            v.sendMessage(Messages.INVALID_BRUSH_PARAM)
                        }
                    }
                }

                "flip" -> {
                    if (params.size > 1) {
                        try {
                            this.flip = FlipMode.valueOf(params[1].uppercase())
                            v.sendMessage(Messages.SCHEMATIC_SET_FLIP.replace("%flip%", this.flip.name.lowercase()))
                        } catch (e: IllegalArgumentException) {
                            v.sendMessage(Messages.INVALID_BRUSH_PARAM)
                        }
                    }
                }

                "mode" -> {
                    if (params.size > 1) {
                        try {
                            this.mode = PasteMode.valueOf(params[1].uppercase())
                            v.sendMessage(Messages.SCHEMATIC_SET_MODE.replace("%mode%", this.mode.name.lowercase()))

                        } catch (e: IllegalArgumentException) {
                            v.sendMessage(Messages.INVALID_BRUSH_PARAM)
                        }
                    }
                }

                else -> {
                    v.sendMessage(Messages.INVALID_BRUSH_PARAM)
                }

            }
        }
    }

    override fun registerArguments(): List<String> {
        return listOf("schem", "rotate", "flip", "list", "mode")
    }

    override fun registerArgumentValues(): HashMap<String, List<String>> {
        return hashMapOf(
            "schem" to schematicLoader.getSchematicNamesForAutoComplete(),
            "rotate" to RotateMode.values().map { it.name.lowercase().replace("degrees_", "") },
            "flip" to FlipMode.values().map { it.name.lowercase() },
            "mode" to PasteMode.values().map { it.name.lowercase() },
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
    Z,
    NONE
}

private enum class RotateMode {
    DEGREES_0,
    DEGREES_90,
    DEGREES_180,
    DEGREES_270,
    DEGREES_RANDOM
}