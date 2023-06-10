package com.github.kevindagame.voxelsniperforge.world;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.util.brushOperation.BlockStateOperation;
import com.github.kevindagame.util.brushOperation.BrushOperation;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.treeType.VoxelTreeType;
import com.github.kevindagame.voxelsniper.world.IWorld;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.chunk.ForgeChunk;
import com.github.kevindagame.voxelsniperforge.entity.ForgeEntity;
import com.github.kevindagame.voxelsniperforge.location.ForgeLocation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public record ForgeWorld(@NotNull ServerLevel level) implements IWorld {
    private static final Random RANDOM = new Random();
    public ServerLevel getLevel() {
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
        var aabb = new AABB(location.getX()-x, location.getY()-y, location.getZ()-z, location.getX()+x, location.getY()+y, location.getZ()+z);
        return this.level.getEntities(null, aabb).stream().map(ForgeEntity::fromForgeEntity).toList();
    }

    @Override
    public void refreshChunk(int x, int z) {
        var chunk = level.getChunk(x, z, ChunkStatus.FULL, false);
        if (chunk != null)
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
    public List<BrushOperation> generateTree(BaseLocation location, VoxelTreeType treeType, boolean updateBlocks) {
        if (treeType.isSupported()) {
            BlockPos pos = ForgeLocation.toForgeBlockPos(location);
            BlockStateListPopulator populator = new BlockStateListPopulator(this);
            boolean result = this.generateTree(populator, this.level.getChunkSource().getGenerator(), pos, new LegacyRandomSource(RANDOM.nextLong()), treeType);
            populator.refreshTiles();
            if (!result) return null;

            List<BrushOperation> ops = new ArrayList<>();
            for (IBlockState newState : populator.getList()) {
                var block = newState.getBlock();
                var oldState = newState.getBlock().getState();
                ops.add(new BlockStateOperation(new BaseLocation(this, block.getX(), block.getY(), block.getZ()), oldState, newState, true, true));
                if (updateBlocks) {
                    newState.update(true, true);
                }
            }
            return ops;
        }
        return null;
    }

    private boolean generateTree(WorldGenLevel access, ChunkGenerator chunkGenerator, BlockPos pos, RandomSource random, VoxelTreeType treeType) {
        ResourceKey<ConfiguredFeature<?, ?>> gen;
        switch (treeType) {
            case TREE -> gen = TreeFeatures.OAK;
            case BIG_TREE -> gen = TreeFeatures.FANCY_OAK;
            case REDWOOD -> gen = TreeFeatures.SPRUCE;
            case TALL_REDWOOD -> gen = TreeFeatures.PINE;
            case BIRCH -> gen = TreeFeatures.BIRCH;
            case JUNGLE -> gen = TreeFeatures.MEGA_JUNGLE_TREE;
            case SMALL_JUNGLE -> gen = TreeFeatures.JUNGLE_TREE_NO_VINE;
            case COCOA_TREE -> gen = TreeFeatures.JUNGLE_TREE;
            case JUNGLE_BUSH -> gen = TreeFeatures.JUNGLE_BUSH;
            case RED_MUSHROOM -> gen = TreeFeatures.HUGE_RED_MUSHROOM;
            case BROWN_MUSHROOM -> gen = TreeFeatures.HUGE_BROWN_MUSHROOM;
            case SWAMP -> gen = TreeFeatures.SWAMP_OAK;
            case ACACIA -> gen = TreeFeatures.ACACIA;
            case DARK_OAK -> gen = TreeFeatures.DARK_OAK;
            case MEGA_REDWOOD -> gen = TreeFeatures.MEGA_PINE;
            case TALL_BIRCH -> gen = TreeFeatures.SUPER_BIRCH_BEES_0002;
            case CHORUS_PLANT -> {
                ChorusFlowerBlock.generatePlant(access, pos, random, 8);
                return true;
            }
            case CRIMSON_FUNGUS -> gen = TreeFeatures.CRIMSON_FUNGUS_PLANTED;
            case WARPED_FUNGUS -> gen = TreeFeatures.WARPED_FUNGUS_PLANTED;
            case AZALEA -> gen = TreeFeatures.AZALEA_TREE;
            case MANGROVE -> gen = TreeFeatures.MANGROVE;
            case TALL_MANGROVE -> gen = TreeFeatures.TALL_MANGROVE;
            case CHERRY -> gen = TreeFeatures.CHERRY;
            default -> gen = TreeFeatures.OAK;
        }

        Holder<ConfiguredFeature<?, ?>> holder = access.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(gen).orElse(null);
        return holder != null && holder.value().place(access, chunkGenerator, random, pos);
    }

    @Override
    public VoxelBiome getBiome(BaseLocation location) {
        var biome = level.getBiome(new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        var biomeId = biome.unwrapKey().get().location();
        return new VoxelBiome(biomeId.getNamespace(), biomeId.getPath(), VoxelSniper.voxelsniper.getVersion());
    }
}
