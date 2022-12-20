package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;

/**
 * Operation that is performed on an entity. This operation is not directly used, but is used as a base for other operations
 */
public abstract class EntityOperation extends AbstractOperation {
    final VoxelVector location;
    final IWorld world;

    public EntityOperation(VoxelVector location, IWorld world) {
        this.location = location;
        this.world = world;
    }
}
