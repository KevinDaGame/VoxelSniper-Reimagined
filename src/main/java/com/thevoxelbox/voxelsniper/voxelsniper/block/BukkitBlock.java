package com.thevoxelbox.voxelsniper.voxelsniper.block;

import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.BukkitBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.BukkitBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.block.Block;

public class BukkitBlock extends AbstractBlock {

    private final Block block;

    public BukkitBlock(Block block) {
        super(new BukkitLocation(block.getLocation()), new BukkitMaterial(block.getType()));
        this.block = block;
    }

    @Override
    public BukkitLocation getLocation() {
        return (BukkitLocation) location;
    }

    @Override
    public BukkitMaterial getMaterial() {
        return (BukkitMaterial) material;
    }

    @Override
    public void setMaterial(IMaterial material) {
        this.material = material;
        block.setType(((BukkitMaterial)material).getMaterial());
    }

    @Override
    public void setMaterial(IMaterial material, boolean applyPhysics) {
        this.material = material;
        block.setType(((BukkitMaterial)material).getMaterial(), applyPhysics);
    }

    @Override
    public BukkitWorld getWorld() {
        return new BukkitWorld(block.getWorld());
    }

    //todo test if this works
    @Override
    public BlockFace getFace(IBlock block) {
        return BlockFace.valueOf(this.block.getFace(((BukkitBlock) block).block).toString());
    }

    @Override
    public IBlock getRelative(BlockFace face) {
        return getWorld().getBlock(getX() + face.getModX(), getY() + face.getModY(), getZ() + face.getModZ());
    }

    @Override
    public IBlockData getBlockData() {
        return new BukkitBlockData(block.getBlockData());
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
    public byte getData() {
        return block.getData();
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
        return new BukkitBlockState(block.getState());
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
