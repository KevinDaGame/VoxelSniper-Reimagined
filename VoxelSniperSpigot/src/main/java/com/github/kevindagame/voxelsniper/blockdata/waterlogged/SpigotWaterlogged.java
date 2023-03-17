package com.github.kevindagame.voxelsniper.blockdata.waterlogged;

import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData;

import org.bukkit.block.data.Waterlogged;

public class SpigotWaterlogged extends SpigotBlockData implements IWaterlogged {
    public SpigotWaterlogged(Waterlogged blockData) {
        super(blockData);
    }

    @Override
    public boolean isWaterlogged() {
        return ((Waterlogged)this.blockData).isWaterlogged();
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        ((Waterlogged)this.blockData).setWaterlogged(waterlogged);
    }
}
