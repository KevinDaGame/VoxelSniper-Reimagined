package com.github.kevindagame.voxelsniperforge;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.Environment;
import com.github.kevindagame.voxelsniper.IVoxelsniper;
import com.github.kevindagame.voxelsniper.Version;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler;
import com.github.kevindagame.voxelsniper.fileHandler.VoxelSniperConfiguration;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniperforge.entity.player.ForgePlayer;
import com.github.kevindagame.voxelsniperforge.fileHandler.ForgeFileHandler;
import com.github.kevindagame.voxelsniperforge.material.BlockMaterial;
import com.github.kevindagame.voxelsniperforge.material.ItemMaterial;
import com.github.kevindagame.voxelsniperforge.permissions.ForgePermissionManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VoxelSniperForge.MODID)
public class VoxelSniperForge implements IVoxelsniper {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "voxelsniperforge";
    private final Logger LOGGER;
    private ForgeFileHandler fileHandler;
    private VoxelSniperConfiguration voxelSniperConfiguration;
    private static VoxelSniperForge instance;

    private final Map<UUID, ForgePlayer> players = new HashMap<>();
    public static VoxelSniperForge getInstance() {
        return instance;
    }

    public VoxelSniperForge() {
        LOGGER = Logger.getLogger(MODID);
        VoxelSniper.voxelsniper = this;
        VoxelSniperForge.instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        VoxelBrushManager brushManager = VoxelBrushManager.initialize();
        getLogger().log(Level.INFO, "Registered {0} Sniper Brushes with {1} handles.", new Object[]{brushManager.registeredSniperBrushes(), brushManager.registeredSniperBrushHandles()});

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public final void registerCommands(final RegisterCommandsEvent event) {
        ForgeCommandManager.initialize(event.getDispatcher());
    }

    @SubscribeEvent
    public final void registerPermissionNodes(final PermissionGatherEvent.Nodes event) {
        ForgePermissionManager.register(event);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        this.fileHandler = new ForgeFileHandler(this);
        MinecraftForge.EVENT_BUS.register(new ForgeVoxelSniperListener(this));
        Messages.load(this);

        voxelSniperConfiguration = new VoxelSniperConfiguration(this);
//        Bukkit.getPluginManager().registerEvents(this.voxelSniperListener, this);
//        Bukkit.getPluginManager().registerEvents(this, this);
//        getLogger().info("Registered Sniper Listener.");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }

    @Nullable
    @Override
    public IPlayer getPlayer(UUID uuid) {
        return getPlayer(ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(uuid));
    }

    @Nullable
    @Override
    public IPlayer getPlayer(String name) {
        return getPlayer(ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByName(name));
    }

    public IPlayer getPlayer(@NotNull ServerPlayer p) {
        if (this.players.get(p.getUUID()) != null) return this.players.get(p.getUUID());
        ForgePlayer res = new ForgePlayer(p);
        this.players.put(res.getUniqueId(), res);
        return res;
    }

    @Override
    public Environment getEnvironment() {
        return Environment.FORGE;
    }

    @Override
    public Version getVersion() {
        return Version.V1_19; // TODO is this always 1.19?
    }

    @Override
    public VoxelSniperConfiguration getVoxelSniperConfiguration() {
        return this.voxelSniperConfiguration;
    }

    @Override
    public IFileHandler getFileHandler() {
        return this.fileHandler;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public List<String> getOnlinePlayerNames() {
        //get online player names
        return Arrays.stream(ServerLifecycleHooks.getCurrentServer().getPlayerNames()).toList();
    }

    @Nullable
    @Override
    public VoxelMaterial getMaterial(String namespace, String key) {
        var resourceLocation = new ResourceLocation(namespace, key);
        if (ForgeRegistries.BLOCKS.containsKey(resourceLocation)) {
            var block = ForgeRegistries.BLOCKS.getValue(resourceLocation);
            return new BlockMaterial(block, namespace, key);
        }
        if (ForgeRegistries.ITEMS.containsKey(resourceLocation)) {
            var item = ForgeRegistries.ITEMS.getValue(resourceLocation);
            return new ItemMaterial(item, namespace, key);
        }
        throw new IllegalArgumentException("Requested material does not exist");
    }

    @Override
    public List<VoxelMaterial> getMaterials() {
        return ForgeRegistries.BLOCKS.getEntries().stream().map(block -> new BlockMaterial(block.getValue(), block.getKey().location().getNamespace(), block.getKey().location().getPath())).collect(Collectors.toList());
    }

    @Nullable
    @Override
    public VoxelBiome getBiome(String namespace, String key) {
        return ForgeRegistries.BIOMES.containsKey(new ResourceLocation(namespace, key)) ? new VoxelBiome(namespace, key) : null;
    }

    @Override
    public List<VoxelBiome> getBiomes() {
        return ForgeRegistries.BIOMES.getEntries().stream().map(biome -> new VoxelBiome(biome.getKey().location().getNamespace(), biome.getKey().location().getPath())).collect(Collectors.toList());
    }

}
