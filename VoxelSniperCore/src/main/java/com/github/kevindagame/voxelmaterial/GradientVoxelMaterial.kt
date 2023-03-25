package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

class GradientVoxelMaterial(private val materials: List<GradientVoxelMaterialMaterial>, val direction: GradientVoxelMaterialDirection): VoxelMaterial() {
    private lateinit var gradient: Array<Array<Array<VoxelMaterialType?>>>

    /**
     * returns the material at the location
     * @param location The location of the material
     */
    override fun getMaterial(location: BaseLocation): VoxelMaterialType {
        val x = location.blockX - lowerBound.blockX
        val y = location.blockY - lowerBound.blockY
        val z = location.blockZ - lowerBound.blockZ

        if (x < 0 || x >= gradient.size || y < 0 || y >= gradient[x].size || z < 0 || z >= gradient[x][y].size) {
            throw IllegalArgumentException("Location is outside the bounds of the gradient")
        }

        return gradient[x][y][z] ?: throw IllegalStateException("VoxelMaterial at location ($x, $y, $z) is null")
    }


    override fun initMaterial(lowerBound: BaseLocation, upperBound: BaseLocation) {
        super.initMaterial(lowerBound, upperBound)
        val sizeX = upperBound.blockX - lowerBound.blockX
        val sizeY = upperBound.blockY - lowerBound.blockY
        val sizeZ = upperBound.blockZ - lowerBound.blockZ
        gradient = Array(sizeX) { Array(sizeY) { arrayOfNulls(sizeZ) } }
        interpolateGradient()
        }

    private fun interpolateGradient() {
        // Calculate the total weight of all materials
        val totalWeight = materials.sumOf { it.weight }

        // Calculate the step size for each material based on its weight
        val stepSizes = materials.map { it.weight.toDouble() / totalWeight }

        // Calculate the starting position for the gradient based on the direction
        var posX = 0
        var posY = 0
        var posZ = 0
        when (direction) {
            GradientVoxelMaterialDirection.NORTH -> posZ = gradient[0][0].size - 1
            GradientVoxelMaterialDirection.SOUTH -> posZ = 0
            GradientVoxelMaterialDirection.EAST -> posX = 0
            GradientVoxelMaterialDirection.WEST -> posX = gradient.size - 1
            GradientVoxelMaterialDirection.UP -> posY = 0
            GradientVoxelMaterialDirection.DOWN -> posY = gradient[0].size - 1
        }

        // Iterate over each element in the gradient and set its material based on the step size
        var stepIndex = 0
        for (x in gradient.indices) {
            for (y in gradient[x].indices) {
                for (z in gradient[x][y].indices) {
                    // Calculate the material index based on the step size and the current step index
                    val materialIndex = (stepIndex / stepSizes.size) % stepSizes.size
                    val stepSize = stepSizes[materialIndex]

                    // Set the material for the current element in the gradient
                    gradient[x][y][z] = materials[materialIndex].material

                    // Update the step index based on the step size
                    stepIndex += (stepSize * totalWeight).toInt()

                    // Update the position based on the direction
                    posX += direction.x
                    posY += direction.y
                    posZ += direction.z

                    // Check if the position is outside the bounds of the gradient
                    if (posX !in gradient.indices || posY !in gradient[x].indices || posZ !in gradient[x][y].indices) {
                        // If so, stop iterating over the gradient
                        return
                    }
                }
            }
        }
    }


}

/**
 * Represents a material with a weight
 * @param material The material
 * @param weight The weight of the material. The weight will determine how many times the material will be used relative to the other materials
 */
data class GradientVoxelMaterialMaterial(val material: VoxelMaterialType, val weight: Int)

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
    DOWN(0, -1, 0)
}