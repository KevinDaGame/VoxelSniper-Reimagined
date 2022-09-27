package com.thevoxelbox.voxelsniper.voxelsniper.blockdata;

import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

public interface IBlockData {
    VoxelMaterial getMaterial();

    boolean matches(IBlockData blockData);

    String getAsString();

    IBlockData merge(IBlockData newData);
}
