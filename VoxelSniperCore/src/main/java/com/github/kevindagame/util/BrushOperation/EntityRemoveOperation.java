package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.location.BaseLocation;

/**
 * Operation that is performed on an entity. This operation removes an entity
 */
public class EntityRemoveOperation extends BrushOperation {

    private final IEntity entity;

    public EntityRemoveOperation(BaseLocation location, IEntity entity) {
        super(location);
        this.entity = entity;
    }

    public IEntity getEntity() {
        return entity;
    }
}
