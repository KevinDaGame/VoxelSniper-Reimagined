package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

/**
 * @author MikeMatrix
 */
public class BlockWrapper {

    private IBlockData blockData;
    private int x;
    private int y;
    private int z;
    private IWorld world;

    /**
     * @param block
     */
    public BlockWrapper(final IBlock block) {
        this.setBlockData(block.getBlockData());
        this.setX(block.getX());
        this.setY(block.getY());
        this.setZ(block.getZ());
        this.setWorld(block.getWorld());
    }

    /**
     * @return the data
     */
    public final IBlockData getBlockData() {
        return this.blockData;
    }

    /**
     * @return the id
     */
    public final VoxelMaterial getMaterial() {
        return this.blockData.getMaterial();
    }

    /**
     * @return the world
     */
    public final IWorld getWorld() {
        return this.world;
    }

    /**
     * @return the x
     */
    public final int getX() {
        return this.x;
    }

    /**
     * @return the y
     */
    public final int getY() {
        return this.y;
    }

    /**
     * @return the z
     */
    public final int getZ() {
        return this.z;
    }

    /**
     * @param blockData the data to set
     */
    public final void setBlockData(final IBlockData blockData) {
        this.blockData = blockData;
    }

    /**
     * @param world the world to set
     */
    public final void setWorld(final IWorld world) {
        this.world = world;
    }

    /**
     * @param x the x to set
     */
    public final void setX(final int x) {
        this.x = x;
    }

    /**
     * @param y the y to set
     */
    public final void setY(final int y) {
        this.y = y;
    }

    /**
     * @param z the z to set
     */
    public final void setZ(final int z) {
        this.z = z;
    }
}
