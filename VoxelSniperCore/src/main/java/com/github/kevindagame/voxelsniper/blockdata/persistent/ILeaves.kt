package com.github.kevindagame.voxelsniper.blockdata.persistent

interface ILeaves {
    fun isPersistent(): Boolean
    fun setPersistent(persistent: Boolean)
}