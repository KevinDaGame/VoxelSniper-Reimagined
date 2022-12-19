package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.location.VoxelLocation;

/**
 * Operation that is performed on a location. This contains anything that isn't covered by other operations
 */
public class LocationOperation extends AbstractOperation {
    private final VoxelLocation location;

    public LocationOperation(VoxelLocation location) {
        this.location = location;
    }

    public VoxelLocation getLocation() {
        return location;
    }


}
