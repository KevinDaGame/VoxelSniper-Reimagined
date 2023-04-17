package com.github.kevindagame.voxelsniper.blockdata.snow

import com.github.kevindagame.voxelsniper.blockdata.IBlockData

interface ISnow: IBlockData {
    fun getLayers(): Int
    fun setLayers(layers: Int)
    fun getMinimumLayers(): Int
    fun getMaximumLayers(): Int

}