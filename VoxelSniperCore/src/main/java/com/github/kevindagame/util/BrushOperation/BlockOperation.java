package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * Operation that is performed on a block. This operation modifies blockData.
 */
public class BlockOperation extends LocationOperation {

    private final IBlockData oldData;
    private IBlockData newData;

    public BlockOperation(VoxelLocation location, IBlockData oldData, IBlockData newData) {
        super(location);
        this.oldData = oldData;
        this.newData = newData;
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

    /**
     *
     * @param newMaterial the material to create blockData for
     */
    public void setNewMaterial(VoxelMaterial newMaterial) {
        this.newData = newMaterial.createBlockData();
    }
}
