package com.github.kevindagame.voxelsniper.blockdata;

import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

public interface IBlockData {
    VoxelMaterial getMaterial();

    boolean matches(IBlockData blockData);

    String getAsString();

    IBlockData merge(IBlockData newData);
}
