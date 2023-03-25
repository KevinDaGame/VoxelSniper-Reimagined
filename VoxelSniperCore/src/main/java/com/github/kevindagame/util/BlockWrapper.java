package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
import com.github.kevindagame.voxelsniper.world.IWorld;

/**
 * @author MikeMatrix
 */
public class BlockWrapper implements Cloneable {

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
     * @param location
     * @param material
     */
    public BlockWrapper(final BaseLocation location, final VoxelMaterialType material) {
        this.setMaterial(material);
        this.setX(location.getBlockX());
        this.setY(location.getBlockY());
        this.setZ(location.getBlockZ());
        this.setWorld(location.getWorld());
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
    public final BlockWrapper setBlockData(final IBlockData blockData) {
        this.blockData = blockData;
        return this;
    }

    /**
     * @return the id
     */
    public final VoxelMaterialType getMaterial() {
        return this.blockData.getMaterial();
    }

    /**
     * @param material the material to set
     */
    public final BlockWrapper setMaterial(final VoxelMaterialType material) {
        setBlockData(material.createBlockData());
        return this;
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
    public final BlockWrapper setWorld(final IWorld world) {
        this.world = world;
        return this;
    }

    /**
     * @return the BaseLocation
     */
    public final BaseLocation getLocation() {
        return new BaseLocation(this.world, this.x, this.y, this.z);
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
    public final BlockWrapper setX(final int x) {
        this.x = x;
        return this;
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
    public final BlockWrapper setY(final int y) {
        this.y = y;
        return this;
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
    public final BlockWrapper setZ(final int z) {
        this.z = z;
        return this;
    }

    @Override
    public BlockWrapper clone() {
        try {
            BlockWrapper clone = (BlockWrapper) super.clone();
            clone.blockData = this.blockData.getCopy();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
