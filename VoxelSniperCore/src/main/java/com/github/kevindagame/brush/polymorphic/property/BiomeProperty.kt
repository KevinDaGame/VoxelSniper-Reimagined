package com.github.kevindagame.brush.polymorphic.property

import com.github.kevindagame.voxelsniper.biome.VoxelBiome

class BiomeProperty : PolyProperty<VoxelBiome>("biome", "Set the biome", VoxelBiome.PLAINS()) {
    override fun set(value: String?) {
        val newBiome = VoxelBiome.getBiome(value) ?: return
        this.value = newBiome
    }


    override fun getValues(): List<String> {
        return VoxelBiome.getBiomes().map { it.key }
    }
}