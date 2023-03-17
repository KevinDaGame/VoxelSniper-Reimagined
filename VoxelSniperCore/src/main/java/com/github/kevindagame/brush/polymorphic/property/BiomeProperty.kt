package com.github.kevindagame.brush.polymorphic.property

import com.github.kevindagame.brush.perform.BasePerformer
import com.github.kevindagame.brush.perform.Performer
import com.github.kevindagame.brush.perform.pMaterial
import com.github.kevindagame.voxelsniper.biome.VoxelBiome

class BiomeProperty : PolyProperty<VoxelBiome>("Biome", "Set the biome", VoxelBiome.PLAINS) {
    override fun set(value: String?) {
        val newBiome = VoxelBiome.getBiome(value) ?: return
        this.value = newBiome
    }


    override fun getValues(): List<String> {
        return VoxelBiome.BIOMES.values.map { it.key }
    }
}