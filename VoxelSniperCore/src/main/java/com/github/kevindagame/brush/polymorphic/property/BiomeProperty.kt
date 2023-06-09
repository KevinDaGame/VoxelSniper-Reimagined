package com.github.kevindagame.brush.polymorphic.property

import com.github.kevindagame.voxelsniper.biome.VoxelBiome

class BiomeProperty : PolyProperty<VoxelBiome>("biome", "Set the biome", VoxelBiome.PLAINS()) {
    override fun set(value: String?) {
        if(value == null) return
        val newBiome = VoxelBiome.getBiome(value) ?: return
        this.value = newBiome
    }


    override fun getValues(): List<String> {
        return VoxelBiome.biomes.map { it.key }
    }
}