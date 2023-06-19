package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.IKeyed;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

public interface IMaterial extends IKeyed {
    boolean isSolid();

    IBlockData createBlockData();

    boolean isAir();

    boolean isTransparent();

    boolean isBlock();

    boolean hasGravity();

    IBlockData createBlockData(String s);
}
