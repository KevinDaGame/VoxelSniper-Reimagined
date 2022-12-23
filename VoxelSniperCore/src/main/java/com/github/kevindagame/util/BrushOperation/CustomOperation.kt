package com.github.kevindagame.util.BrushOperation

import com.github.kevindagame.brush.CustomBrush
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.voxelsniper.location.BaseLocation

/**
 * This class serves as an operation for brushes that don't fit one of the other operations.
 */
class CustomOperation(
    location: BaseLocation,
    private var customBrush: CustomBrush,
    private var snipeData: SnipeData,
    val context: CustomOperationContext
) :
    BrushOperation(location) {
    override fun perform(undo: Undo): Boolean {
        TODO("Not used")
    }
}