package com.github.kevindagame.voxelsniper.blockdata;

import com.github.kevindagame.voxelsniper.blockdata.persistent.SpigotPersistent;
import com.github.kevindagame.voxelsniper.blockdata.redstoneWire.SpigotRedstoneWire;
import com.github.kevindagame.voxelsniper.blockdata.waterlogged.SpigotWaterlogged;
import com.github.kevindagame.voxelsniper.material.SpigotMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.RedstoneWire;

public class SpigotBlockData implements IBlockData {
    protected final BlockData blockData;

    protected SpigotBlockData(BlockData blockData) {
        this.blockData = blockData;
    }

    public static IBlockData fromSpigotData(BlockData blockData) {
        if (blockData instanceof RedstoneWire redstone)
            return new SpigotRedstoneWire(redstone);
        if (blockData instanceof Leaves leaves)
            return new SpigotPersistent(leaves);
        if (blockData instanceof Waterlogged waterlogged)
            return new SpigotWaterlogged(waterlogged);
        return new SpigotBlockData(blockData);
    }

    public BlockData getBlockData() {
        return blockData;
    }

    @Override
    public VoxelMaterial getMaterial() {
        return SpigotMaterial.fromSpigotMaterial(blockData.getMaterial());
    }

    @Override
    public boolean matches(IBlockData blockData) {
        return this.blockData.matches(((SpigotBlockData) blockData).getBlockData());
    }

    @Override
    public String getAsString() {
        return blockData.getAsString();
    }

    @Override
    public IBlockData merge(IBlockData newData) {
        return SpigotBlockData.fromSpigotData(this.blockData.merge(((SpigotBlockData) newData).getBlockData()));
    }

    @Override
    public IBlockData getCopy() {
        return SpigotBlockData.fromSpigotData(blockData.clone());
    }
}
