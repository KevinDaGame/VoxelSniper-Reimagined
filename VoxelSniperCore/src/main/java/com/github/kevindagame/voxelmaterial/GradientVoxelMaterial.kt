package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

class GradientVoxelMaterial(materials: List<GradientVoxelMaterialMaterial>, direction: GradientVoxelMaterialDirection): VoxelMaterial() {
    lateinit var gradient: Array<Array<Array<VoxelMaterial?>>>

    /**
     * returns the material at the location
     * @param x The x position relative to the target block
     * @param y The y position relative to the target block
     * @param z The z position relative to the target block
     */
    override fun getMaterial(x: Int, y: Int, z: Int): VoxelMaterialType {
        TODO("Not yet implemented")
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
        for (x in gradient.indices) {
            for (y in gradient[x].indices) {
                for (z in gradient[x][y].indices) {
                    val voxelMaterial: VoxelMaterial? = gradient[x][y][z]
                    // Do something with the voxelMaterial value
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