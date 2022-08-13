package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

public abstract class AbstractBlock implements IBlock{
    final ILocation location;
    VoxelMaterial material;

    public AbstractBlock(ILocation location, VoxelMaterial material) {
        this.location = location;
        this.material = material;
    }

    @Override
    public final VoxelMaterial getMaterial() {
        return this.material;
    }

    @Override
    public IBlock getRelative(int x, int y, int z) {
        return getWorld().getBlock(getX() + x, getY() + y, getZ() + z);
    }

}
