package com.github.kevindagame.brush

import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.util.brushOperation.operation.CustomOperation

abstract class CustomBrush : AbstractBrush() {
    abstract fun perform(operations: List<CustomOperation>, snipeData: SnipeData, undo: Undo): Boolean
}