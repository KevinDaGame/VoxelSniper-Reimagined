package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.vector.VoxelVector;

/**
 * Operation that is performed on an entity. This operation is not directly used, but is used as a base for other operations
 */
public abstract class EntityOperation extends AbstractOperation {
    final VoxelVector location;

    public EntityOperation(VoxelVector location) {
        this.location = location;
    }
}
