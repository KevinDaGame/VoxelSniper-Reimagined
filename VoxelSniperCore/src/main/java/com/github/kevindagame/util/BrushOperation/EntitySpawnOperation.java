package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;

/**
 * Operation that is performed on an entity. This operation spawns an entity
 */
public class EntitySpawnOperation extends BrushOperation {

    private VoxelEntityType entityType;

    public EntitySpawnOperation(BaseLocation location, VoxelEntityType entityType) {
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
