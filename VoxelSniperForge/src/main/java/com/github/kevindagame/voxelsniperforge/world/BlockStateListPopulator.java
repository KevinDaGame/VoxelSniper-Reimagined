package com.github.kevindagame.voxelsniperforge.world;

import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.blockstate.ForgeBlockState;
import com.github.kevindagame.voxelsniperforge.location.ForgeLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.BlackholeTickAccess;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.TickPriority;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockStateListPopulator implements WorldGenLevel {
    private final ServerLevel level;
    private final ForgeWorld world;
    private final Map<BlockPos, net.minecraft.world.level.block.state.BlockState> dataMap = new HashMap<>();
    private final Map<BlockPos, BlockEntity> entityMap = new HashMap<>();
    private final LinkedHashMap<BlockPos, ForgeBlockState> list;

    public BlockStateListPopulator(ForgeWorld world) {
        this(world, new LinkedHashMap<>());
    }

    private BlockStateListPopulator(ForgeWorld world, LinkedHashMap<BlockPos, ForgeBlockState> list) {
        this.world = world;
        this.level = world.getLevel();
        this.list = list;
    }

    @NotNull
    @Override
    public net.minecraft.world.level.block.state.BlockState getBlockState(@NotNull BlockPos pos) {
        net.minecraft.world.level.block.state.BlockState blockData = this.dataMap.get(pos);
        return (blockData != null) ? blockData : this.level.getBlockState(pos);
    }

    @NotNull
    @Override
    public FluidState getFluidState(@NotNull BlockPos pos) {
        net.minecraft.world.level.block.state.BlockState blockData = this.dataMap.get(pos);
        return (blockData != null) ? blockData.getFluidState() : this.level.getFluidState(pos);
    }

    @Override
    public BlockEntity getBlockEntity(@NotNull BlockPos pos) {
        // The contains is important to check for null values
        if (this.entityMap.containsKey(pos)) {
            return this.entityMap.get(pos);
        }

        return this.level.getBlockEntity(pos);
    }

    @Override
    public boolean setBlock(@NotNull BlockPos pos, @NotNull net.minecraft.world.level.block.state.BlockState state, int flags) {
        pos = pos.immutable();
        // remove first to keep insertion order
        this.list.remove(pos);

        this.dataMap.put(pos, state);
        if (state.hasBlockEntity()) {
            this.entityMap.put(pos, ((EntityBlock) state.getBlock()).newBlockEntity(pos, state));
        } else {
            this.entityMap.put(pos, null);
        }

        // use 'this' to ensure that the block state is the correct TileState
        var b = (ForgeBlock) this.world.getBlock(ForgeLocation.fromForgeBlockPos(this.world, pos));
        var s = this.getBlockState(pos);
        BlockEntity tileEntity = this.getBlockEntity(pos);
        ForgeBlockState state1 = ForgeBlockState.fromForgeBlock(b, s, tileEntity);
        state1.setFlag(flags);
        // set world handle to ensure that updated calls are done to the world and not to this populator
        state1.setWorldHandle(level);
        this.list.put(pos, state1);
        return true;
    }

    public void refreshTiles() {
        for (ForgeBlockState state : this.list.values()) {
            if (state != null) {
                state.refreshSnapshot();
            }
        }
    }

    public List<IBlockState> getList() {
        return new ArrayList<>(this.list.values());
    }

    // For tree generation
    @Override
    public int getMinBuildHeight() {
        return this.level.getMinBuildHeight();
    }

    @Override
    public int getHeight() {
        return this.level.getHeight();
    }

    @Override
    public boolean isStateAtPosition(@NotNull BlockPos pos, Predicate<net.minecraft.world.level.block.state.BlockState> state) {
        return state.test(this.getBlockState(pos));
    }

    @Override
    public boolean isFluidAtPosition(@NotNull BlockPos pos, Predicate<FluidState> state) {
        return state.test(this.getFluidState(pos));
    }

    @NotNull
    @Override
    public DimensionType dimensionType() {
        return this.level.dimensionType();
    }

    @NotNull
    @Override
    public RegistryAccess registryAccess() {
        return this.level.registryAccess();
    }

    @NotNull
    @Override
    public <T extends BlockEntity> java.util.Optional<T> getBlockEntity(@NotNull BlockPos pos, @NotNull net.minecraft.world.level.block.entity.BlockEntityType<T> type) {
        BlockEntity tileentity = this.getBlockEntity(pos);
        return tileentity != null && tileentity.getType() == type ? (Optional<T>)Optional.of(tileentity) : java.util.Optional.empty();
    }

    @NotNull
    @Override
    public BlockPos getHeightmapPos(@NotNull net.minecraft.world.level.levelgen.Heightmap.Types heightmap, @NotNull BlockPos pos) {
        return level.getHeightmapPos(heightmap, pos);
    }

    @Nullable
    @Override
    public ChunkAccess getChunk(int i, int i1, ChunkStatus chunkStatus, boolean b) {
        return null;
    }

    @Override
    public int getHeight(@NotNull net.minecraft.world.level.levelgen.Heightmap.Types heightmap, int x, int z) {
        return level.getHeight(heightmap, x, z);
    }

    @NotNull
    @Override
    public net.minecraft.world.level.storage.LevelData getLevelData() {
        return level.getLevelData();
    }

    @Override
    public int getRawBrightness(@NotNull BlockPos pos, int ambientDarkness) {
        return level.getRawBrightness(pos, ambientDarkness);
    }

    @Override
    public int getBrightness(@NotNull net.minecraft.world.level.LightLayer type, @NotNull BlockPos pos) {
        return level.getBrightness(type, pos);
    }

    @Override
    public long getSeed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public ServerLevel getLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long nextSubTickCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public LevelTickAccess<Block> getBlockTicks() {
        return BlackholeTickAccess.emptyLevelList();
    }

    @Override
    public void scheduleTick(@NotNull BlockPos pos, @NotNull Block block, int delay) {
        // Used by BlockComposter
    }

    @NotNull
    @Override
    public LevelTickAccess<Fluid> getFluidTicks() {
        return BlackholeTickAccess.emptyLevelList();
    }

    @NotNull
    @Override
    public DifficultyInstance getCurrentDifficultyAt(@NotNull BlockPos pos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MinecraftServer getServer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public ChunkSource getChunkSource() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public RandomSource getRandom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void playSound(Player except, @NotNull BlockPos pos, @NotNull SoundEvent sound, @NotNull SoundSource category, float volume, float pitch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addParticle(@NotNull ParticleOptions parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void levelEvent(Player player, int eventId, @NotNull BlockPos pos, int data) {
        // Used by PowderSnowBlock.removeFluid
    }

    @Override
    public void gameEvent(Holder<GameEvent> holder, Vec3 vec3, GameEvent.Context context) {

    }

    @NotNull
    @Override
    public List<Entity> getEntities(Entity except, @NotNull AABB box, @NotNull Predicate<? super Entity> predicate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public <T extends Entity> List<T> getEntities(@NotNull EntityTypeTest<Entity, T> filter, @NotNull AABB box, @NotNull Predicate<? super T> predicate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public List<? extends Player> players() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSkyDarken() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public BiomeManager getBiomeManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public Holder<Biome> getUncachedNoiseBiome(int biomeX, int biomeY, int biomeZ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isClientSide() {
        return false;
    }

    @Override
    public int getSeaLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public FeatureFlagSet enabledFeatures() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getShade(@NotNull Direction direction, boolean shaded) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public LevelLightEngine getLightEngine() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public WorldBorder getWorldBorder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setBlock(@NotNull BlockPos pos, @NotNull BlockState state, int flags, int maxUpdateDepth) {
        return false;
    }

    @Override
    public boolean removeBlock(@NotNull BlockPos pos, boolean move) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean destroyBlock(@NotNull BlockPos pos, boolean drop, Entity breakingEntity, int maxUpdateDepth) {
        return false;
    }

    @Override
    public void scheduleTick(@NotNull BlockPos pos, @NotNull Fluid fluid, int delay) {
    }

    @Override
    public void scheduleTick(@NotNull BlockPos pos, @NotNull Block block, int delay, @NotNull TickPriority priority) {
    }

    @Override
    public void scheduleTick(@NotNull BlockPos pos, @NotNull Fluid fluid, int delay, @NotNull TickPriority priority) {
    }
}
