package com.thevoxelbox.voxelsniper.voxelsniper.material;

import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;

public interface IMaterial {
    boolean isSolid();
    String getKey();
    boolean equals(String key);
    IBlockData createBlockData();

    String getName();

    boolean equals(VoxelMaterial material);

    boolean isTransparent();
}
