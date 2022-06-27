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

    @Override
    public IBlock getRelative(int x, int y, int z) {
        return getWorld().getBlock(getX() + x, getY() + y, getZ() + z);
    }

}
