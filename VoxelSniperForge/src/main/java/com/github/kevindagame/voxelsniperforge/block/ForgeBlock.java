package com.github.kevindagame.voxelsniperforge.block;

import com.github.kevindagame.voxelsniper.block.AbstractBlock;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniperforge.blockdata.ForgeBlockData;
import com.github.kevindagame.voxelsniperforge.blockstate.ForgeBlockState;
import com.github.kevindagame.voxelsniperforge.material.BlockMaterial;
import com.github.kevindagame.voxelsniperforge.world.ForgeWorld;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends AbstractBlock {

    private final BlockPos pos;

    public ForgeBlock(VoxelLocation location, BlockPos pos) {
        super(location, BlockMaterial.fromForgeBlock(((ForgeWorld)location.getWorld()).getLevel().getBlockState(pos).getBlock()));
        this.pos = pos;
    }

    private Level getLevel() {
        return ((ForgeWorld)getLocation().getWorld()).getLevel();
    }

    @Override
    public void setMaterial(VoxelMaterial material) {
        this.setMaterial(material, true);
    }

    @Override
    public void setMaterial(VoxelMaterial material, boolean applyPhysics) {
        this.setBlockData(material.createBlockData(), applyPhysics);
    }


    @Override
    public void setBlockData(IBlockData blockData) {
        this.setBlockData(blockData, true);
    }

    @Override
    public void setBlockData(IBlockData blockData, boolean applyPhysics) {
        var world = getLevel();
        var position = this.pos;
        BlockState old = getLevel().getBlockState(this.pos);
        BlockState newState = ((ForgeBlockData) blockData).getState();

        if (old.hasBlockEntity() && newState.getBlock() != old.getBlock()) {
            world.removeBlockEntity(position);
        }

        if (applyPhysics) {
            world.setBlock(position, newState, 3);
        } else {
            throw new UnsupportedOperationException("Not implemented yet");
//            boolean success = world.setBlock(position, newState, 2 | 16); // NOTIFY | NO_OBSERVER | NO_PLACE (custom)
//            if (success) {
//                world.sendBlockUpdated(position, old, newState, 3);
//            }
        }

        this.material = blockData.getMaterial();
    }

    @Override
    public IBlockData getBlockData() {
        return ForgeBlockData.fromData(getLevel().getBlockState(this.pos));
    }

    @Override
    public boolean isEmpty() {
        return getMaterial().isAir();
    }

    @Override
    public boolean isLiquid() {
        return getLevel().getBlockState(this.pos).getMaterial().isLiquid();
    }

    @Override
    public IBlockState getState() {
        BlockState blockState = this.getLevel().getBlockState(this.pos);
        BlockEntity tileEntity = this.getLevel().getBlockEntity(this.pos);
        return ForgeBlockState.fromForgeBlock(this, blockState, tileEntity);
    }

    @Nullable
    @Override
    public BlockFace getFace(IBlock block) {
        for (BlockFace face : BlockFace.values()) {
            if ((this.getX() + face.getModX() == block.getX()) && (this.getY() + face.getModY() == block.getY()) && (this.getZ() + face.getModZ() == block.getZ())) {
                return face;
            }
        }
        return null;
    }

    @Override
    public boolean isBlockFacePowered(BlockFace face) {
        return this.getLevel().hasSignal(this.pos, blockFaceToDirection(face));
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        int power = this.getLevel().getSignal(this.pos, blockFaceToDirection(face));

        ForgeBlock relative = (ForgeBlock) this.getRelative(face);
        if (relative.getMaterial() == VoxelMaterial.REDSTONE_WIRE) {
            int relativePower = relative.getLevel().getSignal(this.pos, blockFaceToDirection(face));
            return Math.max(power, relativePower) > 0;
        }

        return power > 0;
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        return this.getLevel().hasNeighborSignal(this.pos);
    }

    @Override
    public boolean isBlockPowered() {
        return this.getLevel().getDirectSignalTo(this.pos) > 0;
    }

    public static Direction blockFaceToDirection(BlockFace face) {
        return switch (face) {
            case DOWN -> Direction.DOWN;
            case UP -> Direction.UP;
            case NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case WEST -> Direction.WEST;
            case EAST -> Direction.EAST;
            default -> throw new IllegalArgumentException("Invalid BlockFace");
        };
    }
}
