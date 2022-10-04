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
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;


import java.util.Iterator;
import java.util.List;

public record ForgeWorld(@NotNull ServerLevel level) implements IWorld {

    public ServerLevel getLevel() {
        return level;
    }
    @Override
    public IBlock getBlock(VoxelLocation location) {
        return new ForgeBlock(level.getBlockState(new BlockPos(location.getX(), location.getY(), location.getZ())).getBlock());
    }

    @Override
    public IBlock getBlock(int x, int y, int z) {
        return new ForgeBlock(level.getBlockState(new BlockPos(x, y, z)).getBlock());
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
        LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        bolt.setPos(location.getX(), location.getY(), location.getZ());
        level.addFreshEntity(bolt);
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void spawn(VoxelLocation location, VoxelEntityType entity) {
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
    public void setBiome(VoxelLocation location, VoxelBiome selectedBiome) {
        //TODO test this
        var chunk = ((ForgeChunk)getChunkAtLocation(location.getBlockX(), location.getBlockZ())).getChunk();
        var biomes = (PalettedContainer<Holder<Biome>>) chunk.getSection(chunk.getSectionIndex(location.getBlockY())).getBiomes();
        biomes.getAndSetUnchecked(
                location.getBlockX() & 3, location.getBlockY() & 3, location.getBlockZ() & 3,
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
    public void generateTree(VoxelLocation location, VoxelTreeType treeType, Undo undo) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Iterator<IBlock> getBlockIterator(VoxelVector origin, VoxelVector direction, double yOffset, int maxDistance) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
