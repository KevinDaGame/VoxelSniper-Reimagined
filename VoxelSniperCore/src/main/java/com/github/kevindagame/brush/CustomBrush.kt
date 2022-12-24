package com.github.kevindagame.brush

import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.util.brushOperation.CustomOperation
import com.google.common.collect.ImmutableList

abstract class CustomBrush : AbstractBrush() {
    abstract fun perform(operations: ImmutableList<CustomOperation>, snipeData: SnipeData, undo: Undo): Boolean
}