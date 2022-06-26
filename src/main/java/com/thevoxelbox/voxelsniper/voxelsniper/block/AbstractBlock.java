package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;

public abstract class AbstractBlock implements IBlock{
    final ILocation location;
    IMaterial material;

    public AbstractBlock(ILocation location, IMaterial material) {
        this.location = location;
        this.material = material;
    }
}
