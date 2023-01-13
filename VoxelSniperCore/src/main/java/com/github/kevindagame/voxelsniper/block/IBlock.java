package com.github.kevindagame.voxelsniper.block;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IBlock {
    BaseLocation getLocation();

    VoxelMaterial getMaterial();

    void setMaterial(VoxelMaterial material);

    void setMaterial(VoxelMaterial material, boolean applyPhysics);

    default IWorld getWorld() {
        return getLocation().getWorld();
    }

    default int getX() {
        return getLocation().getBlockX();
    }

    default int getY() {
        return getLocation().getBlockY();
    }

    default int getZ() {
        return getLocation().getBlockZ();
    }

    @Nullable
    BlockFace getFace(IBlock block);

    IBlock getRelative(int x, int y, int z);

    IBlock getRelative(BlockFace face);

    default IChunk getChunk() {
        return getWorld().getChunkAtLocation(getLocation());
    }

    IBlockData getBlockData();

    void setBlockData(IBlockData blockData);

    void setBlockData(IBlockData blockData, boolean applyPhysics);

    boolean isEmpty();

    boolean isLiquid();

    IBlockState getState();

    boolean isBlockFacePowered(BlockFace face);

    boolean isBlockFaceIndirectlyPowered(BlockFace face);

    boolean isBlockIndirectlyPowered();

    boolean isBlockPowered();

    default List<IEntity> getNearbyEntities(double x, double y, double z) {
        return this.getWorld().getNearbyEntities(this.getLocation(), x, y, z);
    }
}
