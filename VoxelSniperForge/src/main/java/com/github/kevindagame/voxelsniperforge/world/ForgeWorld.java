package com.github.kevindagame.voxelsniperforge.world;

import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.chunk.ForgeChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Iterator;
import java.util.List;

public record ForgeWorld(Level level) implements IWorld {
    @Override
    public IBlock getBlock(VoxelLocation location) {
        return new ForgeBlock(level.getBlockState(new BlockPos(location.getX(), location.getY(), location.getZ())).getBlock());
    }

    @Override
    public IBlock getBlock(int x, int y, int z) {
        return return new ForgeBlock(level.getBlockState(new BlockPos(x, y, z)).getBlock());
    }

    @Override
    public int getMinWorldHeight() {
        return level.getMinBuildHeight();
    }

    @Override
    public int getMaxWorldHeight() {
        return level().getMaxBuildHeight();
    }

    @Override
    public IChunk getChunkAtLocation(int x, int z) {
        return new ForgeChunk(level.getChunkAt(new BlockPos(x, 0, z)));
    }

    @Override
    public List<IEntity> getNearbyEntities(VoxelLocation location, double x, double y, double z) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void refreshChunk(int x, int z) {
        //Does this work?
        level.getChunkAt(new BlockPos(x, 0, z)).setUnsaved(true);
    }

    @Override
    public void strikeLightning(VoxelLocation location) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void spawn(VoxelLocation location, VoxelEntityType entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setBiome(int x, int z, VoxelBiome selectedBiome) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void regenerateChunk(int x, int z) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void generateTree(VoxelLocation location, VoxelTreeType treeType, Undo undo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Iterator<IBlock> getBlockIterator(VoxelVector origin, VoxelVector direction, double yOffset, int maxDistance) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
