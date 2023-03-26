package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType
import kotlin.math.abs

class GradientVoxelMaterial(
    private val materials: List<GradientVoxelMaterialMaterial>,
    val direction: GradientVoxelMaterialDirection
) : VoxelMaterial() {
    private lateinit var gradient: Array<IBlockData?>

    /**
     * returns the material at the location
     * @param location The location of the material
     */
    override fun getMaterial(location: BaseLocation): IBlockData {
        val index = when (direction) {
            GradientVoxelMaterialDirection.NORTH -> abs(location.blockZ - lowerBound.blockZ)
            GradientVoxelMaterialDirection.SOUTH -> abs(location.blockZ - upperBound.blockZ)
            GradientVoxelMaterialDirection.EAST -> abs(location.blockX - upperBound.blockX)
            GradientVoxelMaterialDirection.WEST -> abs(location.blockX - lowerBound.blockX)
            GradientVoxelMaterialDirection.UP -> abs(location.blockY - upperBound.blockY)
            GradientVoxelMaterialDirection.DOWN -> abs(location.blockY - lowerBound.blockY)
        }
        val material = gradient[index]
        return material ?: VoxelMaterialType.SPONGE.createBlockData()

    }


    override fun initMaterial(lowerBound: BaseLocation, upperBound: BaseLocation) {
        super.initMaterial(lowerBound, upperBound)
        calculateGradient()
    }

    private fun calculateGradient() {
        val totalWeight = materials.sumOf { it.weight }
        val gradientSize = when (direction) {
            GradientVoxelMaterialDirection.NORTH, GradientVoxelMaterialDirection.SOUTH -> {
                abs(upperBound.blockZ - lowerBound.blockZ) + 1
            }
            GradientVoxelMaterialDirection.EAST, GradientVoxelMaterialDirection.WEST -> {
                abs(upperBound.blockX - lowerBound.blockX) + 1
            }
            GradientVoxelMaterialDirection.UP, GradientVoxelMaterialDirection.DOWN -> {
                abs(upperBound.blockY - lowerBound.blockY) + 1
            }
        }
        gradient = Array(gradientSize) { null }
        var index = 0
        for (material in materials) {
            val amount = (material.weight.toDouble() / totalWeight * gradientSize).toInt()
            for (i in 0 until amount) {
                gradient[index] = material.material
                index++
            }
        }

    }
}

/**
 * Represents a material with a weight
 * @param material The material
 * @param weight The weight of the material. The weight will determine how many times the material will be used relative to the other materials
 */
data class GradientVoxelMaterialMaterial(val material: IBlockData, val weight: Int)

/**
 * The direction the gradient will go.
 * @param x The x direction
 * @param y The y direction
 * @param z The z direction
 *
 * For example UP will start at the lowest block and go up to the highest block
 * NORTH will start at the SOUTH block and go to the NORTH block
 *
 */
enum class GradientVoxelMaterialDirection(val x: Int, val y: Int, val z: Int) {
    NORTH(0, 0, -1),
    SOUTH(0, 0, 1),
    EAST(1, 0, 0),
    WEST(-1, 0, 0),
    UP(0, 1, 0),
    DOWN(0, -1, 0);
}