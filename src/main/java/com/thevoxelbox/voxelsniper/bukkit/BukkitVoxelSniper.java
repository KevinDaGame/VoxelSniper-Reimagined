package com.thevoxelbox.voxelsniper.bukkit;

import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Version;
import com.thevoxelbox.voxelsniper.voxelsniper.player.BukkitPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.player.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Level;

/**
 * Bukkit extension point.
 */
public class BukkitVoxelSniper extends JavaPlugin implements IVoxelsniper {

    private static BukkitVoxelSniper instance;
    
    private final VoxelSniperListener voxelSniperListener = new VoxelSniperListener(this);
    private VoxelSniperConfiguration voxelSniperConfiguration;

    /**
     * @return {@link BukkitVoxelSniper}
     */
    public static BukkitVoxelSniper getInstance() {
        return BukkitVoxelSniper.instance;
    }

    /**
     * Returns object for accessing global VoxelSniper options.
     *
     * @return {@link VoxelSniperConfiguration} object for accessing global VoxelSniper options.
     */
    public VoxelSniperConfiguration getVoxelSniperConfiguration() {
        return voxelSniperConfiguration;
    }

    @Override
    public void onEnable() {
        BukkitVoxelSniper.instance = this;

        // Initialize profile manager (Sniper)
        VoxelProfileManager.initialize();
        
        // Initialize brush manager
        VoxelBrushManager brushManager = VoxelBrushManager.initialize();
        getLogger().log(Level.INFO, "Registered {0} Sniper Brushes with {1} handles.", new Object[]{brushManager.registeredSniperBrushes(), brushManager.registeredSniperBrushHandles()});

        saveDefaultConfig();
        voxelSniperConfiguration = new VoxelSniperConfiguration(getConfig());

        Bukkit.getPluginManager().registerEvents(this.voxelSniperListener, this);
        getLogger().info("Registered Sniper Listener.");

        // Initialize commands
        VoxelCommandManager.initialize();
    }

    @Override
    public BukkitPlayer getPlayer(UUID uuid) {
        return new BukkitPlayer(Bukkit.getPlayer(uuid));
    }

    @Override
    public Environment getEnvironment() {
        return Environment.BUKKIT;
    }

    @Override
    public Version getVersion() {
        //todo: Does this work?
        String version = "V" + Bukkit.getBukkitVersion().split("-")[0].replace('.' , '_').split("_")[0];
        return Version.valueOf(version);
    }
}
