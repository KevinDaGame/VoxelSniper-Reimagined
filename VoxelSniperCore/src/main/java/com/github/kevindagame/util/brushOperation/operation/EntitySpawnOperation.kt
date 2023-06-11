package com.github.kevindagame.util.brushOperation.operation

import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType
import com.github.kevindagame.voxelsniper.location.BaseLocation

/**
 * Operation that is performed on an entity. This operation spawns an entity
 */
class EntitySpawnOperation(location: BaseLocation, var entityType: VoxelEntityType) : BrushOperation(location) {

    override fun perform(undo: Undo): Boolean {
        location.world.spawn(location, entityType)
        return false
    }
}