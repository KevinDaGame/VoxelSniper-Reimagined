package com.github.kevindagame.brush.polymorphic.operation

import com.github.kevindagame.brush.polymorphic.PolyBrush
import com.github.kevindagame.brush.polymorphic.PolyOperationType
import com.github.kevindagame.voxelsniper.material.VoxelMaterial

/**
 * Base class for operations such as blending and splatter
 */
abstract class PolyOperation(val supportedOperationTypes: List<PolyOperationType>) {
    abstract fun apply(brushSize: Int, brush: PolyBrush, excludeAir: Boolean, excludeWater: Boolean): Array<Array<Array<VoxelMaterial>>>
}