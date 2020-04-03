package com.thevoxelbox.voxelsniper;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bukkit extension point.
 */
public class VoxelSniper extends JavaPlugin {

    private static VoxelSniper instance;
    private VoxelProfileManager sniperManager = new VoxelProfileManager(this);
    private final VoxelSniperListener voxelSniperListener = new VoxelSniperListener(this);
    private VoxelSniperConfiguration voxelSniperConfiguration;

    /**
     * Returns {@link com.thevoxelbox.voxelsniper.VoxelBrushManager} for current instance.
     *
     * @return Brush Manager for current instance.
     */
    public VoxelBrushManager getBrushManager() {
        return VoxelBrushManager.getInstance();
    }

    /**
     * @return {@link VoxelSniper}
     */
    public static VoxelSniper getInstance() {
        return VoxelSniper.instance;
    }

    /**
     * Returns object for accessing global VoxelSniper options.
     *
     * @return {@link VoxelSniperConfiguration} object for accessing global VoxelSniper options.
     */
    public VoxelSniperConfiguration getVoxelSniperConfiguration() {
        return voxelSniperConfiguration;
    }

    /**
     * Returns {@link com.thevoxelbox.voxelsniper.VoxelProfileManager} for current instance.
     *
     * @return SniperManager
     */
    public VoxelProfileManager getSniperManager() {
        return sniperManager;
    }

    @Override
    public void onEnable() {
        VoxelSniper.instance = this;

        VoxelBrushManager brushManager = VoxelBrushManager.initialize();
        getLogger().log(Level.INFO, "Registered {0} Sniper Brushes with {1} handles.", new Object[]{brushManager.registeredSniperBrushes(), brushManager.registeredSniperBrushHandles()});

        saveDefaultConfig();
        voxelSniperConfiguration = new VoxelSniperConfiguration(getConfig());

        Bukkit.getPluginManager().registerEvents(this.voxelSniperListener, this);
        getLogger().info("Registered Sniper Listener.");

        VoxelCommandManager.registerCommands();
        VoxelCommandManager.registerBrushSubcommands();
    }
}
