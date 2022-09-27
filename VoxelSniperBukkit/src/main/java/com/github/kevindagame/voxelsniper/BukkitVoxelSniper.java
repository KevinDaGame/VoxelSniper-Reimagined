package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.VoxelProfileManager;
import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.entity.player.BukkitPlayer;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.IEventManager;
import com.github.kevindagame.voxelsniper.events.bukkit.BukkitEventManager;
import com.github.kevindagame.voxelsniper.fileHandler.BukkitFileHandler;
import com.github.kevindagame.voxelsniper.fileHandler.IFileHandler;
import com.github.kevindagame.voxelsniper.fileHandler.VoxelSniperConfiguration;
import com.github.kevindagame.voxelsniper.material.BukkitMaterial;
import com.github.kevindagame.voxelsniper.material.IMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.BukkitWorld;
import com.github.kevindagame.voxelsniper.world.IWorld;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Bukkit extension point.
 */
public class BukkitVoxelSniper extends JavaPlugin implements IVoxelsniper, Listener {

    private static BukkitVoxelSniper instance;
    private static BukkitAudiences adventure;

    private final VoxelSniperListener voxelSniperListener = new VoxelSniperListener(this);
    private final Map<String, IWorld> worlds = new HashMap<>();
    private final Map<UUID, BukkitPlayer> players = new HashMap<>();
    private VoxelSniperConfiguration voxelSniperConfiguration;
    private IFileHandler fileHandler;
    private IEventManager eventManager;

    /**
     * @return {@link BukkitVoxelSniper}
     */
    public static BukkitVoxelSniper getInstance() {
        return BukkitVoxelSniper.instance;
    }

    public static BukkitAudiences getAdventure() {
        return BukkitVoxelSniper.adventure;
    }

    @Override
    public void onEnable() {
        VoxelSniper.voxelsniper = this;
        BukkitVoxelSniper.instance = this;
        this.fileHandler = new BukkitFileHandler(this);
        this.eventManager = new BukkitEventManager();
        // Initialize profile manager (Sniper)
        VoxelProfileManager.initialize();

        Messages.load(this);
        BukkitVoxelSniper.adventure = BukkitAudiences.create(this);
        // Initialize brush manager
        VoxelBrushManager brushManager = VoxelBrushManager.initialize();
        getLogger().log(Level.INFO, "Registered {0} Sniper Brushes with {1} handles.", new Object[]{brushManager.registeredSniperBrushes(), brushManager.registeredSniperBrushHandles()});

        saveDefaultConfig();
        voxelSniperConfiguration = new VoxelSniperConfiguration(this);

        Bukkit.getPluginManager().registerEvents(this.voxelSniperListener, this);
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Registered Sniper Listener.");

        // Initialize commands
        BukkitCommandManager.initialize();
    }

    @Override
    public void onDisable() {
        if (BukkitVoxelSniper.adventure != null) {
            BukkitVoxelSniper.adventure.close();
            BukkitVoxelSniper.adventure = null;
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        this.players.remove(event.getPlayer().getUniqueId());
    }

    @NotNull
    public BukkitPlayer getPlayer(Player player) {
        if (this.players.get(player.getUniqueId()) != null) return this.players.get(player.getUniqueId());
        BukkitPlayer res = new BukkitPlayer(player);
        this.players.put(player.getUniqueId(), res);
        return res;
    }

    @Override
    @Nullable
    public IPlayer getPlayer(UUID uuid) {
        if (this.players.get(uuid) != null) return this.players.get(uuid);
        Player bukkitPlayer = Bukkit.getPlayer(uuid);
        if (bukkitPlayer == null) return null;
        BukkitPlayer res = new BukkitPlayer(bukkitPlayer);
        this.players.put(uuid, res);
        return res;
    }

    @Override
    @Nullable
    public IPlayer getPlayer(String name) {
        Player bukkitPlayer = Bukkit.getPlayer(name);
        if (bukkitPlayer == null) return null;
        BukkitPlayer res = new BukkitPlayer(bukkitPlayer);
        this.players.put(bukkitPlayer.getUniqueId(), res);
        return res;
    }

    @Override
    public List<String> getOnlinePlayerNames() {
        return getServer().getOnlinePlayers().stream().map(Player::getName).toList();
    }

    @Override
    public IMaterial getMaterial(VoxelMaterial material) {
        Material mat = Material.matchMaterial(material.getKey());
        if (mat != null) return new BukkitMaterial(mat);
        return null;
    }

    @Override
    public Environment getEnvironment() {
        return Environment.BUKKIT;
    }

    @Override
    public Version getVersion() {
        //todo: Does this work?
        String version = "V" + Bukkit.getBukkitVersion().substring(0, 4).replace('.', '_');
        return Version.valueOf(version);
    }

    /**
     * Returns object for accessing global VoxelSniper options.
     *
     * @return {@link VoxelSniperConfiguration} object for accessing global VoxelSniper options.
     */
    @Override
    public VoxelSniperConfiguration getVoxelSniperConfiguration() {
        return voxelSniperConfiguration;
    }

    @Override
    public IFileHandler getFileHandler() {
        return fileHandler;
    }

    @Override
    public IEventManager getEventManager() {
        return this.eventManager;
    }

    @NotNull
    public IWorld getWorld(@NotNull World world) {
        if (this.worlds.get(world.getName()) != null) return this.worlds.get(world.getName());
        BukkitWorld res = new BukkitWorld(world);
        this.worlds.put(world.getName(), res);
        return res;
    }
}
