package com.github.kevindagame.voxelsniper.blockdata.snow

import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData
import org.bukkit.block.data.type.Snow

class SpigotSnow(private val snow: Snow) : SpigotBlockData(snow), ISnow {
    override fun getLayers(): Int {
        return snow.layers
    }

    override fun setLayers(layers: Int) {
        snow.layers = layers
    }

    override fun getMinimumLayers(): Int {
        return snow.minimumLayers
    }

    override fun getMaximumLayers(): Int {
        return snow.maximumLayers
    }
}