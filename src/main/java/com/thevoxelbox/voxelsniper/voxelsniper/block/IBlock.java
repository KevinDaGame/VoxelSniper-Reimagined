package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.location.VoxelLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import javax.annotation.Nullable;

public interface IBlock {
    VoxelLocation getLocation();
    VoxelMaterial getMaterial();
    void setMaterial(VoxelMaterial material);
    void setMaterial(VoxelMaterial material, boolean applyPhysics);

    default IWorld getWorld() { return getLocation().getWorld(); }

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
    default IChunk getChunk(){
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
}
