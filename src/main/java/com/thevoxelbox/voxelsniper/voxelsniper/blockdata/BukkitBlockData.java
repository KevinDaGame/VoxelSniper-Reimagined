package com.thevoxelbox.voxelsniper.voxelsniper.blockdata;

import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import org.bukkit.block.data.BlockData;

public class BukkitBlockData implements IBlockData {
    private final BlockData blockData;

    public BukkitBlockData(BlockData blockData) {
        this.blockData = blockData;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    @Override
    public VoxelMaterial getMaterial() {
        return BukkitMaterial.fromBukkitMaterial(blockData.getMaterial());
    }

    @Override
    public boolean matches(IBlockData blockData) {
        return this.blockData.matches(((BukkitBlockData) blockData).getBlockData());
    }

    @Override
    public String getAsString() {
        return blockData.getAsString();
    }

    @Override
    public IBlockData merge(IBlockData newData) {
        return new BukkitBlockData(this.blockData.merge(((BukkitBlockData) newData).getBlockData()));
    }
}
