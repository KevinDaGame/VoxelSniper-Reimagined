package com.thevoxelbox.voxelsniper.voxelsniper.blockdata;

import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;

public interface IBlockData {
    IMaterial getMaterial();

    boolean matches(IBlockData blockData);

    String getAsString();
}
