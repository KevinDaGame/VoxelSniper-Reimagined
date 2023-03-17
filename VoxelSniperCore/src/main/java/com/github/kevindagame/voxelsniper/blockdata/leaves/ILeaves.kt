package com.github.kevindagame.voxelsniper.blockdata.leaves

interface ILeaves {
    fun isPersistent(): Boolean
    fun setPersistent(persistent: Boolean)
}