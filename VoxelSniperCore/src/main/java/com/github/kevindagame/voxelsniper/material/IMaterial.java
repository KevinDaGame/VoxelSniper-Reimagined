package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

public interface IMaterial extends Comparable<IMaterial> {
    boolean isSolid();

    String getKey();

    boolean equals(String key);

    IBlockData createBlockData();

    String getName();

    boolean equals(VoxelMaterialType material);

    boolean isTransparent();

    boolean isBlock();

    boolean hasGravity();

    IBlockData createBlockData(String s);

    @Override
    default int compareTo(IMaterial m) {
        return getKey().compareTo(m.getKey());
    }
}
