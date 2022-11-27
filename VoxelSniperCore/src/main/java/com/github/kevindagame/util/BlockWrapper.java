package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;

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
     * @param b
     * @param blx
     * @param bly
     * @param blz
     */
    public BlockWrapper(final IBlock b, final int blx, final int bly, final int blz, final IWorld world) {
        this.blockData = b.getBlockData();
        this.x = blx;
        this.y = bly;
        this.z = blz;
        this.world = world;
    }

    /**
     * @return the data
     */
    public final IBlockData getBlockData() {
        return this.blockData;
    }

    /**
     * @param blockData the data to set
     */
    public final void setBlockData(final IBlockData blockData) {
        this.blockData = blockData;
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
     * @param world the world to set
     */
    public final void setWorld(final IWorld world) {
        this.world = world;
    }

    /**
     * @return the x
     */
    public final int getX() {
        return this.x;
    }

    /**
     * @param x the x to set
     */
    public final void setX(final int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public final int getY() {
        return this.y;
    }

    /**
     * @param y the y to set
     */
    public final void setY(final int y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public final int getZ() {
        return this.z;
    }

    /**
     * @param z the z to set
     */
    public final void setZ(final int z) {
        this.z = z;
    }
}
