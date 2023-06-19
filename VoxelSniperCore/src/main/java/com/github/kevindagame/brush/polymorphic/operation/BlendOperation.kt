package com.github.kevindagame.brush.polymorphic.operation

import com.github.kevindagame.brush.polymorphic.PolyBrush
import com.github.kevindagame.brush.polymorphic.PolyOperationType
import com.github.kevindagame.brush.polymorphic.property.PolyPropertiesEnum
import com.github.kevindagame.snipe.SnipeAction
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

class BlendOperation: PolyOperation(listOf(PolyOperationType.BLOCK)) {
    override fun apply(
        brushSize: Int,
        brush: PolyBrush,
        snipeAction: SnipeAction,
        excludeAir: Boolean,
        excludeWater: Boolean
    ): Array<Array<Array<VoxelMaterial>>> {
        val doExcludeAir = snipeAction == SnipeAction.GUNPOWDER
        val targetBlock = brush.targetBlock
        val brushSizeDoubled = 2 * brushSize
        // Array that holds the original materials plus a buffer
        val oldMaterials = Array(2 * (brushSize + 1) + 1) {
            Array(2 * (brushSize + 1) + 1) {
                arrayOfNulls<VoxelMaterial>(2 * (brushSize + 1) + 1)
            }
        }

        // populate oldmats with the current materials
        for (x in 0..2 * (brushSize + 1)) {
            for (y in 0..2 * (brushSize + 1)) {
                for (z in 0..2 * (brushSize + 1)) {
                    oldMaterials[x][y][z] = brush.getBlockMaterialAt(
                        targetBlock.x - brushSize - 1 + x,
                        targetBlock.y - brushSize - 1 + y,
                        targetBlock.z - brushSize - 1 + z
                    )
                }
            }
        }
        //clone oldMaterials into newMaterials
        val newMaterials = oldMaterials.clone()

        // Blend materials
        for (x in 0..brushSizeDoubled) {
            for (y in 0..brushSizeDoubled) {
                for (z in 0..brushSizeDoubled) {
                    val materialFrequency: MutableMap<VoxelMaterial?, Int> = HashMap()
                    var tiecheck = true
                    for (m in -1..1) {
                        for (n in -1..1) {
                            for (o in -1..1) {
                                if (!(m == 0 && n == 0 && o == 0)) {
                                    val currentMaterial = oldMaterials[x + 1 + m][y + 1 + n][z + 1 + o]
                                    val currentFrequency = materialFrequency.getOrDefault(currentMaterial, 0) + 1
                                    materialFrequency[currentMaterial] = currentFrequency
                                }
                            }
                        }
                    }
                    var highestMaterialCount = 0
                    var highestMaterial = VoxelMaterial.AIR()

                    // Find most common neighbouring material
                    for ((key, value) in materialFrequency) {
                        if (value > highestMaterialCount && !(doExcludeAir && key!!.isAir) && !(excludeWater && key!!.equals(VoxelMaterial.WATER()))) {
                            highestMaterialCount = value
                            highestMaterial = key
                        }
                    }

                    // Make sure that there's no tie in the highest material
                    for ((key, value) in materialFrequency) {
                        if (value == highestMaterialCount && !(doExcludeAir && key!!.isAir) && !(excludeWater && key!!.equals(VoxelMaterial.WATER()))) {
                            if (key!!.equals(highestMaterial)) {
                                continue
                            }
                            tiecheck = false
                        }
                    }

                    // Record most common neighbor material for this block
                    if (tiecheck) {
                        newMaterials[x][y][z] = highestMaterial
                    }
                }
            }
        }
        return newMaterials as Array<Array<Array<VoxelMaterial>>>
    }

    override fun getProperties(): Collection<PolyPropertiesEnum> {
        return listOf(PolyPropertiesEnum.EXCLUDEWATER)
    }

}