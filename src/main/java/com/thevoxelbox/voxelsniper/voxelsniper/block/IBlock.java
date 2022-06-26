package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;

public interface IBlock {
    ILocation getLocation();
    IMaterial getMaterial();
    void setMaterial(IMaterial material);
}
