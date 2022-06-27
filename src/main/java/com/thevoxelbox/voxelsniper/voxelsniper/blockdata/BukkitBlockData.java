package com.thevoxelbox.voxelsniper.voxelsniper.blockdata;

import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import org.bukkit.block.data.BlockData;

public class BukkitBlockData implements IBlockData {
    private BlockData blockData;

    public BukkitBlockData(BlockData blockData) {
        this.blockData = blockData;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    @Override
    public IMaterial getMaterial() {
        return new BukkitMaterial(blockData.getMaterial());
    }

    @Override
    public boolean matches(IBlockData blockData) {
        return this.blockData.matches(((BukkitBlockData) blockData).getBlockData());
    }

    @Override
    public String getAsString() {
        return blockData.getAsString();
    }
}
