package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.bukkit.VoxelBrushManager;
import com.thevoxelbox.voxelsniper.bukkit.VoxelCommandManager;
import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.bukkit.VoxelSniperConfiguration;
import com.thevoxelbox.voxelsniper.bukkit.VoxelSniperListener;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;

import java.util.logging.Level;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bukkit extension point.
 */
public class VoxelSniper extends JavaPlugin {
    public static IVoxelsniper voxelsniper;

    private static VoxelSniper instance;
    private static BukkitAudiences adventure;

    private final VoxelSniperListener voxelSniperListener = new VoxelSniperListener(this);
    private VoxelSniperConfiguration voxelSniperConfiguration;

    /**
     * @return {@link VoxelSniper}
     */
    public static VoxelSniper getInstance() {
        return VoxelSniper.instance;
    }

    public static BukkitAudiences getAdventure() {
        return VoxelSniper.adventure;
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
        VoxelSniper.instance = this;
        VoxelSniper.adventure = BukkitAudiences.create(this);

        Messages.load(this);

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
    public void onDisable() {
        if(VoxelSniper.adventure != null) {
            VoxelSniper.adventure.close();
            VoxelSniper.adventure = null;
        }
    }
}
