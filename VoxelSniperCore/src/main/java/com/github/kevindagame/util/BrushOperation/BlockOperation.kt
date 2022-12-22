package com.github.kevindagame.util.BrushOperation;

import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * Operation that is performed on a block. This operation modifies blockData.
 */
public class BlockOperation extends BrushOperation {

    private final IBlockData oldData;
    private IBlockData newData;
    private boolean applyPhysics;

    public BlockOperation(BaseLocation location, IBlockData oldData, IBlockData newData) {
        super(location);
        this.oldData = oldData;
        this.newData = newData;
        this.applyPhysics = true;
    }

    public BlockOperation(BaseLocation location, IBlockData oldData, IBlockData newData, boolean applyPhysics) {
        this(location, oldData, newData);
        this.applyPhysics = applyPhysics;
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

    public boolean applyPhysics() {
        return applyPhysics;
    }

    /**
     * @param newMaterial the material to create blockData for
     */
    public void setNewMaterial(VoxelMaterial newMaterial) {
        this.newData = newMaterial.createBlockData();
    }

    @Override
    public boolean perform(Undo undo) {
        var block = getLocation().getBlock();
        undo.put(block);
        block.setBlockData(getNewData(), applyPhysics());
        return false;
    }
}
