package com.github.kevindagame.voxelsniper.blockdata.waterlogged;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

public interface IWaterlogged extends IBlockData {
    boolean isWaterlogged();
    void setWaterlogged(boolean waterlogged);
}
