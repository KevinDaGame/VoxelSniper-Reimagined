package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

public interface IMaterial extends Comparable<IMaterial> {
    boolean isSolid();

    String getKey();
    String getNamespace();

    boolean equals(String namespace, String key);

    IBlockData createBlockData();


    boolean equals(VoxelMaterial material);

    boolean isAir();

    boolean isFluid();

    boolean isTransparent();

    boolean isBlock();

    boolean hasGravity();

    IBlockData createBlockData(String s);

    @Override
    default int compareTo(IMaterial m) {
        return getKey().compareTo(m.getKey());
    }
}
