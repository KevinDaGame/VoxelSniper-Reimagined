package com.github.kevindagame.util.brushOperation

import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.voxelsniper.entity.IEntity
import com.github.kevindagame.voxelsniper.location.BaseLocation

/**
 * Operation that is performed on an entity. This operation removes an entity
 */
class EntityRemoveOperation(location: BaseLocation, val entity: IEntity) : BrushOperation(location) {

    override fun perform(undo: Undo): Boolean {
        entity.remove()
        return false
    }
}