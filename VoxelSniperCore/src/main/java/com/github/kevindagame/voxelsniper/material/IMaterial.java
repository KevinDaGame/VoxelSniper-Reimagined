package com.github.kevindagame.voxelsniper.material;

import com.github.kevindagame.IKeyed;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

public interface IMaterial extends IKeyed {

    IBlockData createBlockData();

    boolean isAir();

    boolean isTransparent();

    boolean isBlock();

    IBlockData createBlockData(String s);
}
