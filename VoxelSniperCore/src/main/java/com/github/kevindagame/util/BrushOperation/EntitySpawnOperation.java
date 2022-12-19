package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

/**
 * Operation that is performed on an entity. This operation spawns an entity
 */
public class EntitySpawnOperation extends EntityOperation {

    private VoxelEntityType entityType;

    public EntitySpawnOperation(VoxelVector location, VoxelEntityType entityType) {
        super(location);
        this.entityType = entityType;
    }

    public VoxelEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(VoxelEntityType entityType) {
        this.entityType = entityType;
    }
}
