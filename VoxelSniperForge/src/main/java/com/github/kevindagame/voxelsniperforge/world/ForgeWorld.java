package com.github.kevindagame.voxelsniperforge.world;

import com.github.kevindagame.VoxelSniper;
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
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public record ForgeWorld(@NotNull ServerLevel level) implements IWorld {

    public Level getLevel() {
        return level;
    }

    @Override
    public IBlock getBlock(BaseLocation location) {
        if (location.getWorld() != this)
            throw new IllegalArgumentException("location doesn't belong to this World");
        return new ForgeBlock(location, new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
        return new ForgeChunk(level.getChunkAt(new BlockPos(x * 16, 0, z * 16)), this);
    }

    @Override
    public List<IEntity> getNearbyEntities(BaseLocation location, double x, double y, double z) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void refreshChunk(int x, int z) {
        var chunk = level.getChunk(x, z, ChunkStatus.FULL, false);
        level.getChunkSource().chunkMap.resendBiomesForChunks(List.of(chunk));
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
        if (tag.isPresent()) {
            Entity created = tag.get().create(level);
            assert created != null;
            created.setPos(location.getX(), location.getY(), location.getZ());
            level.addFreshEntity(created);
        } else {
            throw new IllegalArgumentException("Invalid entity type");
        }
    }

    //code from mojang itself: FillBiomeCommand::makeResolver. idk what it does but it works
    private static BiomeResolver makeResolver(MutableInt p_262615_, ChunkAccess p_262698_, BoundingBox p_262622_, Holder<Biome> p_262705_, Predicate<Holder<Biome>> p_262695_) {
        return (p_262550_, p_262551_, p_262552_, p_262553_) -> {
            int i = QuartPos.toBlock(p_262550_);
            int j = QuartPos.toBlock(p_262551_);
            int k = QuartPos.toBlock(p_262552_);
            Holder<Biome> holder = p_262698_.getNoiseBiome(p_262550_, p_262551_, p_262552_);
            if (p_262622_.isInside(i, j, k) && p_262695_.test(holder)) {
                p_262615_.increment();
                return p_262705_;
            } else {
                return holder;
            }
        };
    }

    @Override
    public void setBiome(int x, int y, int z, VoxelBiome selectedBiome) {
        var biome = level.registryAccess().registry(Registries.BIOME)
                .orElseThrow()
                .getHolderOrThrow(ResourceKey.create(Registries.BIOME, new ResourceLocation(selectedBiome.getKey())));
        var chunkAccess = level.getChunk(new BlockPos(x, y, z));
        chunkAccess.fillBiomesFromNoise(makeResolver(new MutableInt(), chunkAccess, new BoundingBox(x, y, z, x, y, z), biome, biomeHolder -> true), level.getChunkSource().randomState().sampler());
        chunkAccess.setUnsaved(true);
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
    public VoxelBiome getBiome(BaseLocation location) {
        var biome = level.getBiome(new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        var biomeId = biome.unwrapKey().get().location();
        return new VoxelBiome(biomeId.getNamespace(), biomeId.getPath(), VoxelSniper.voxelsniper.getVersion());
    }
}
