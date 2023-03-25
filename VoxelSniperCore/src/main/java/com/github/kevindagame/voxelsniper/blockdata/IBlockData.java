package com.github.kevindagame.voxelsniper.blockdata;

import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;

public interface IBlockData {
    VoxelMaterialType getMaterial();

    boolean matches(IBlockData blockData);

    String getAsString();

    IBlockData merge(IBlockData newData);

    //no default clone because it's protected
    IBlockData getCopy();
}
