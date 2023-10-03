package com.github.kevindagame.util.brushOperation.operation

import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.voxelsniper.biome.VoxelBiome
import com.github.kevindagame.voxelsniper.location.BaseLocation

/**
 * Represents a brush operation that changes the biome of a location.
 */
class BiomeOperation(location: BaseLocation, val oldBiome: VoxelBiome, var newBiome: VoxelBiome) :
    BrushOperation(location) {

    override fun perform(undo: Undo): Boolean {
        val location = location
        location.world.setBiome(location.blockX, location.blockY, location.blockZ, newBiome)
        return true
    }
}