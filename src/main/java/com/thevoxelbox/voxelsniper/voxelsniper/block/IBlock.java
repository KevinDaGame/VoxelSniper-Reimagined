package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.World;

public interface IBlock {
    ILocation getLocation();
    IMaterial getMaterial();
    void setMaterial(IMaterial material);
    void setMaterial(IMaterial material, boolean applyPhysics);

    IWorld getWorld();

    default int getX() {
        return getLocation().getX();
    }
    default int getY() {
        return getLocation().getY();
    }
    default int getZ() {
        return getLocation().getZ();
    }
    BlockFace getFace(IBlock block);
    IBlock getRelative(int x, int y, int z);
    IBlock getRelative(BlockFace face);
    default IChunk getChunk(){
        return getWorld().getChunkAtLocation(getLocation());
    }
    IBlockData getBlockData();
    void setBlockData(IBlockData blockData);
    void setBlockData(IBlockData blockData, boolean applyPhysics);
    @Deprecated
    byte getData();

    boolean isEmpty();

    boolean isLiquid();

    IBlockState getState();

    boolean isBlockFacePowered(BlockFace face);

    boolean isBlockFaceIndirectlyPowered(BlockFace face);

    boolean isBlockIndirectlyPowered();

    boolean isBlockPowered();
}
