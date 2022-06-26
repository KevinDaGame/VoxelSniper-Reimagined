package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.World;

public interface IBlock {
    ILocation getLocation();
    IMaterial getMaterial();
    void setMaterial(IMaterial material);

    IWorld getWorld();

    default int getX() {
        return getLocation().getX();
    }
    default int getY() {
        return getLocation().getY();
    }
    default int getZ() {
        return getLocation().getZ();
    }
}
