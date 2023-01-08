package com.github.kevindagame.voxelsniperforge.world;

import com.github.kevindagame.util.brushOperation.BrushOperation;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.chunk.ForgeChunk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.levelgen.Heightmap;

import org.jetbrains.annotations.NotNull;


import java.util.Iterator;
import java.util.List;

public record ForgeWorld(@NotNull Level level) implements IWorld {

    public Level getLevel() {
        return level;
    }
    @Override
    public IBlock getBlock(BaseLocation location) {
        if (location.getWorld() != this)
            throw new IllegalArgumentException("location doesn't belong to this World");
        return new ForgeBlock(location, new BlockPos(location.getX(), location.getY(), location.getZ()));
    }

    @Override
    public IBlock getBlock(int x, int y, int z) {
        return getBlock(new BaseLocation(this, x, y, z));
    }

    @Override
    public int getMinWorldHeight() {
        return level.getMinBuildHeight();
    }

    @Override
    public int getMaxWorldHeight() {
        return level.getMaxBuildHeight();
    }

    @Override
    public IChunk getChunkAtLocation(int x, int z) {
        return new ForgeChunk(level.getChunkAt(new BlockPos(x, 0, z)), this);
    }

    @Override
    public List<IEntity> getNearbyEntities(BaseLocation location, double x, double y, double z) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void refreshChunk(int x, int z) {
        //Does this work?
        level.getChunkAt(new BlockPos(x, 0, z)).setUnsaved(true);
    }

    @Override
    public void strikeLightning(BaseLocation location) {
        LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        bolt.setPos(location.getX(), location.getY(), location.getZ());
        level.addFreshEntity(bolt);
    }

    @Override
    public String getName() {
        var str = this.level.toString();
        return str.substring("ServerLevel[".length(), str.length() - 1);
    }

    @Override
    public void spawn(BaseLocation location, VoxelEntityType entity) {
        //TODO test this
        var tag = EntityType.byString(entity.getKey());
        if (tag.isPresent()){
            Entity created = tag.get().create(level);
            assert created != null;
            created.setPos(location.getX(), location.getY(), location.getZ());
            level.addFreshEntity(created);
        }
        else{
            throw new IllegalArgumentException("Invalid entity type");
        }
    }

    @Override
    public void setBiome(int x, int y, int z, VoxelBiome selectedBiome) {
        //TODO test this
        var chunk = ((ForgeChunk)getChunkAtLocation(x, z)).getChunk();
        var biomes = (PalettedContainer<Holder<Biome>>) chunk.getSection(chunk.getSectionIndex(y)).getBiomes();
        biomes.getAndSetUnchecked(
                x & 3, y & 3, z & 3,
                level.registryAccess().registry(Registry.BIOME_REGISTRY)
                        .orElseThrow()
                        .getHolderOrThrow(ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(selectedBiome.getKey())))
        );
        chunk.setUnsaved(true);
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        //TODO test this
        return level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(x, 0, z)).getY();
    }

    @Override
    public void regenerateChunk(int x, int z) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<BrushOperation> generateTree(BaseLocation location, VoxelTreeType treeType, boolean b) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Iterator<IBlock> getBlockIterator(VoxelVector origin, VoxelVector direction, double yOffset, int maxDistance) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public VoxelBiome getBiome(BaseLocation location) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
