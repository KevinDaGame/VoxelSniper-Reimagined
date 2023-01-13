package com.github.kevindagame.voxelsniper.block;

import com.github.kevindagame.voxelsniper.SpigotVoxelSniper;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.blockstate.SpigotBlockState;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.SpigotMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Nullable;

public class SpigotBlock extends AbstractBlock {

    private final Block block;

    public SpigotBlock(Block block) {
        super(new BaseLocation(SpigotVoxelSniper.getInstance().getWorld(block.getWorld()), block.getX(), block.getY(), block.getZ()), SpigotMaterial.fromSpigotMaterial(block.getType()));
        this.block = block;
    }

    @Override
    public void setMaterial(VoxelMaterial material) {
        this.material = material;
        block.setType(((SpigotMaterial) material.getIMaterial()).material());
    }

    @Override
    public void setMaterial(VoxelMaterial material, boolean applyPhysics) {
        this.material = material;
        block.setType(((SpigotMaterial) material.getIMaterial()).material(), applyPhysics);
    }

    @Override
    @Nullable
    public BlockFace getFace(IBlock block) {
        var face = this.block.getFace(((SpigotBlock) block).block);
        return face == null ? null : BlockFace.valueOf(face.name());
    }

    @Override
    public IBlockData getBlockData() {
        return SpigotBlockData.fromSpigotData(block.getBlockData());
    }

    @Override
    public void setBlockData(IBlockData blockData) {
        this.material = blockData.getMaterial();
        block.setBlockData(((SpigotBlockData) blockData).getBlockData());
    }

    @Override
    public void setBlockData(IBlockData blockData, boolean applyPhysics) {
        this.material = blockData.getMaterial();
        block.setBlockData(((SpigotBlockData) blockData).getBlockData(), applyPhysics);
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
        return SpigotBlockState.fromSpigotState(this, block.getState());
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
