package com.github.kevindagame.util.brushOperation

import com.github.kevindagame.brush.CustomBrush
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.voxelsniper.location.BaseLocation

/**
 * This class serves as an operation for brushes that don't fit one of the other operations.
 */
@Suppress("unused")
class CustomOperation(
    location: BaseLocation,
    private var customBrush: CustomBrush,
    private var snipeData: SnipeData,
    val context: CustomOperationContext
) :
    BrushOperation(location) {
    override fun perform(undo: Undo): Boolean {
        throw UnsupportedOperationException("Not implemented")
    }
}