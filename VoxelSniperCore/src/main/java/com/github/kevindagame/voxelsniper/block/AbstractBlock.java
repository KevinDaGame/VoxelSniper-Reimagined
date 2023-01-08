package com.github.kevindagame.voxelsniper.block;

import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

public abstract class AbstractBlock implements IBlock {
    final BaseLocation location;
    VoxelMaterial material;

    public AbstractBlock(BaseLocation location, VoxelMaterial material) {
        this.location = location;
        this.material = material;
    }

    @Override
    public final VoxelMaterial getMaterial() {
        if (this.material.isAir()) return VoxelMaterial.AIR;
        return this.material;
    }

    @Override
    public final BaseLocation getLocation() {
        return location;
    }

    @Override
    public IBlock getRelative(int x, int y, int z) {
        return getWorld().getBlock(getX() + x, getY() + y, getZ() + z);
    }

    @Override
    public IBlock getRelative(BlockFace face) {
        return getWorld().getBlock(getX() + face.getModX(), getY() + face.getModY(), getZ() + face.getModZ());
    }
}
