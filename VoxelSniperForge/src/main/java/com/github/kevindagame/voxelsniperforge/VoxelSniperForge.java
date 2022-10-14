package com.github.kevindagame.voxelsniperforge;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.VoxelProfileManager;
import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.Environment;
import com.github.kevindagame.voxelsniper.IVoxelsniper;
import com.github.kevindagame.voxelsniper.Version;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.DummyEventManager;
import com.github.kevindagame.voxelsniper.events.IEventManager;
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler;
import com.github.kevindagame.voxelsniper.fileHandler.VoxelSniperConfiguration;
import com.github.kevindagame.voxelsniper.material.IMaterial;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;

import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VoxelSniperForge.MODID)
public class VoxelSniperForge implements IVoxelsniper {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "voxelsniperforge";
    private final Logger LOGGER;
    private ForgeFileHandler fileHandler;
    private VoxelSniperConfiguration voxelSniperConfiguration;
    private final DummyEventManager eventManager = new DummyEventManager();
    private static VoxelSniperForge instance;

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
        VoxelProfileManager.initialize();
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

    public IPlayer getPlayer(ServerPlayer p) {
        // TODO keep track of players
        return new ForgePlayer(p);
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
    public IMaterial getMaterial(VoxelMaterial material) {
        var key = new ResourceLocation(material.getNamespace(), material.getKey());
        if (ForgeRegistries.BLOCKS.containsKey(key)) {
            var item = ForgeRegistries.BLOCKS.getValue(key);
            return new BlockMaterial(item);
        }
        if (ForgeRegistries.ITEMS.containsKey(key)) {
            var item = ForgeRegistries.ITEMS.getValue(key);
            return new ItemMaterial(item);
        }
        throw new IllegalArgumentException("Requested material does not exist");
    }

    @Override
    public IEventManager getEventManager() {
        return eventManager;
    }
}
