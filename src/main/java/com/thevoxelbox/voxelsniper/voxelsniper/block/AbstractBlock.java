package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

public abstract class AbstractBlock implements IBlock{
    final VoxelLocation location;
    VoxelMaterial material;

    public AbstractBlock(VoxelLocation location, VoxelMaterial material) {
        this.location = location;
        this.material = material;
    }

    @Override
    public final VoxelMaterial getMaterial() {
        if (this.material.isAir()) return VoxelMaterial.AIR;
        return this.material;
    }

    @Override
    public final VoxelLocation getLocation() {
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
