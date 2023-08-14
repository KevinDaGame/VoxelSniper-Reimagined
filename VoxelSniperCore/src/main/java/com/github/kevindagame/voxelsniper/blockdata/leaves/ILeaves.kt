package com.github.kevindagame.voxelsniper.blockdata.leaves

import com.github.kevindagame.voxelsniper.blockdata.IBlockData

interface ILeaves : IBlockData {
    fun isPersistent(): Boolean
    fun setPersistent(persistent: Boolean)
}