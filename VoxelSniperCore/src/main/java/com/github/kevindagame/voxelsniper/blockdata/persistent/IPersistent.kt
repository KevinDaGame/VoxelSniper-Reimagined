package com.github.kevindagame.voxelsniper.blockdata.persistent

interface IPersistent {
    fun isPersistent(): Boolean
    fun setPersistent(persistent: Boolean)
}