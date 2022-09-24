package com.thevoxelbox.voxelsniper.voxelsniper.material;

import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;

public interface IMaterial extends Comparable<IMaterial> {
    boolean isSolid();

    String getKey();

    boolean equals(String key);

    IBlockData createBlockData();

    String getName();

    boolean equals(VoxelMaterial material);

    boolean isTransparent();

    boolean isBlock();

    boolean hasGravity();

    IBlockData createBlockData(String s);

    @Override
    default int compareTo(IMaterial m) {
        return getKey().compareTo(m.getKey());
    }
}
