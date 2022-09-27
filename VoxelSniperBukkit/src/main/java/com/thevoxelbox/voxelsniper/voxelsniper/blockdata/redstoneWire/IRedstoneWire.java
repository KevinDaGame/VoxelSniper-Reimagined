package com.thevoxelbox.voxelsniper.voxelsniper.blockdata.redstoneWire;

import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;

public interface IRedstoneWire extends IBlockData {

    /**
     * Gets the value of the 'power' property.
     *
     * @return the 'power' value
     */
    int getPower();

    /**
     * Sets the value of the 'power' property.
     *
     * @param power the new 'power' value
     */
    void setPower(int power);
}
