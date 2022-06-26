package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import org.bukkit.block.Block;

public class BukkitBlock extends AbstractBlock {

    public BukkitBlock(ILocation location, IMaterial material) {
        super(location, material);
    }

    public BukkitBlock(Block block) {
        super(new BukkitLocation(block.getLocation()), new BukkitMaterial(block.getType()));
    }

    @Override
    public BukkitLocation getLocation() {
        return (BukkitLocation) location;
    }

    @Override
    public BukkitMaterial getMaterial() {
        return (BukkitMaterial) material;
    }

    @Override
    public void setMaterial(IMaterial material) {
        this.material = material;
    }
}
