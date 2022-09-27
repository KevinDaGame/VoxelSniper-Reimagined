package com.github.kevindagame.voxelsniper.block;

import com.github.kevindagame.voxelsniper.BukkitVoxelSniper;
import com.github.kevindagame.voxelsniper.blockdata.BukkitBlockData;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockstate.BukkitBlockState;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.BukkitMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Nullable;

public class BukkitBlock extends AbstractBlock {

    private final Block block;

    public BukkitBlock(Block block) {
        super(new VoxelLocation(BukkitVoxelSniper.getInstance().getWorld(block.getWorld()), block.getX(), block.getY(), block.getZ()), BukkitMaterial.fromBukkitMaterial(block.getType()));
        this.block = block;
    }

    @Override
    public void setMaterial(VoxelMaterial material) {
        this.material = material;
        block.setType(((BukkitMaterial) material.getIMaterial()).material());
    }

    @Override
    public void setMaterial(VoxelMaterial material, boolean applyPhysics) {
        this.material = material;
        block.setType(((BukkitMaterial) material.getIMaterial()).material(), applyPhysics);
    }

    //todo test if this works
    @Override
    @Nullable
    public BlockFace getFace(IBlock block) {
        var face = this.block.getFace(((BukkitBlock) block).block);
        return face == null ? null : BlockFace.valueOf(face.toString());
    }

    @Override
    public IBlockData getBlockData() {
        return BukkitBlockData.fromBukkitData(block.getBlockData());
    }

    @Override
    public void setBlockData(IBlockData blockData) {
        block.setBlockData(((BukkitBlockData) blockData).getBlockData());
    }

    @Override
    public void setBlockData(IBlockData blockData, boolean applyPhysics) {
        block.setBlockData(((BukkitBlockData) blockData).getBlockData(), applyPhysics);
    }

    @Override
    public boolean isEmpty() {
        return block.isEmpty();
    }

    @Override
    public boolean isLiquid() {
        return block.isLiquid();
    }

    @Override
    public IBlockState getState() {
        return BukkitBlockState.fromBukkitState(this, block.getState());
    }

    @Override
    public boolean isBlockFacePowered(BlockFace face) {
        return block.isBlockFacePowered(org.bukkit.block.BlockFace.valueOf(face.toString()));
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        return block.isBlockFaceIndirectlyPowered(org.bukkit.block.BlockFace.valueOf(face.toString()));
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        return block.isBlockIndirectlyPowered();
    }

    @Override
    public boolean isBlockPowered() {
        return block.isBlockPowered();
    }


}
