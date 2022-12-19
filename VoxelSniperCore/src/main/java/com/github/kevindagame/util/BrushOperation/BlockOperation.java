package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;

/**
 * Operation that is performed on a block. This operation modifies blockData.
 */
public class BlockOperation extends AbstractOperation {

    private final VoxelLocation location;
    private final IBlockData oldData;
    private IBlockData newData;

    public BlockOperation(VoxelLocation location, IBlockData oldData, IBlockData newData) {
        this.location = location;
        this.oldData = oldData;
        this.newData = newData;
    }

    public VoxelLocation getLocation() {
        return location;
    }

    public IBlockData getOldData() {
        return oldData;
    }

    public IBlockData getNewData() {
        return newData;
    }

    public void setNewData(IBlockData newData) {
        this.newData = newData;
    }
}
