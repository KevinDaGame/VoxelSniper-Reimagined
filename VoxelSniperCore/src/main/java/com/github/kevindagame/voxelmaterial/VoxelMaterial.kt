package com.github.kevindagame.voxelmaterial

import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType

abstract class VoxelMaterial {
    protected lateinit var lowerBound: BaseLocation
    protected lateinit var upperBound: BaseLocation

    /**
     * Initializes the material with the given bounds.
     * This method should be called before the brush is used
     * @param lowerBound The lower bound of the brush
     * @param upperBound The upper bound of the brush
     */
    open fun initMaterial(lowerBound: BaseLocation, upperBound: BaseLocation) {
        this.lowerBound = lowerBound
        this.upperBound = upperBound
    }
    abstract fun getMaterial(x: Int, y: Int, z: Int): VoxelMaterialType
}