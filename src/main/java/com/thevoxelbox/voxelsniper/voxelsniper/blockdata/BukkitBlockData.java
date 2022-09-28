package com.thevoxelbox.voxelsniper.voxelsniper.blockdata;

import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.redstoneWire.BukkitRedstoneWire;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.RedstoneWire;

public class BukkitBlockData implements IBlockData {
    protected final BlockData blockData;

    protected BukkitBlockData(BlockData blockData) {
        this.blockData = blockData;
    }

    public static IBlockData fromBukkitData(BlockData blockData) {
        if (blockData instanceof RedstoneWire redstone)
            return new BukkitRedstoneWire(redstone);
        return new BukkitBlockData(blockData);
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
