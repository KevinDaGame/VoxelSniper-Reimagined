package com.github.kevindagame.util.brushOperation.operation

import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.voxelsniper.events.Cancellable
import com.github.kevindagame.voxelsniper.location.BaseLocation

/**
 * This class is the base class for all operations
 */
abstract class BrushOperation(val location: BaseLocation) : Cancellable {
    private var cancelled = false
    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    /**
     * Performs the operation.
     *
     * @param undo the undo object to add the operation to
     *
     * @return whether to reload the brush area
     */
    internal abstract fun perform(undo: Undo): Boolean
}

enum class CustomOperationContext {
    PLAYERLOCATION,
    TARGETLOCATION,
    OTHER
}