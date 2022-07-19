package com.thevoxelbox.voxelsniper.bukkit;

import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Version;
import com.thevoxelbox.voxelsniper.voxelsniper.fileHandler.BukkitFileHandler;
import com.thevoxelbox.voxelsniper.voxelsniper.fileHandler.IFileHandler;
import com.thevoxelbox.voxelsniper.voxelsniper.location.LocationFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.player.BukkitPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VectorFactory;
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
    private IFileHandler fileHandler;

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
    @Override
    public void onEnable() {
        VoxelSniper.voxelsniper = this;
        BukkitVoxelSniper.instance = this;
        LocationFactory.main = this;
        VectorFactory.main = this;
        this.fileHandler = new BukkitFileHandler(this);
        VoxelMaterial.setMain(this);
        // Initialize profile manager (Sniper)
        VoxelProfileManager.initialize(this);

        
        // Initialize brush manager
        VoxelBrushManager brushManager = VoxelBrushManager.initialize(this);
        getLogger().log(Level.INFO, "Registered {0} Sniper Brushes with {1} handles.", new Object[]{brushManager.registeredSniperBrushes(), brushManager.registeredSniperBrushHandles()});

        saveDefaultConfig();
        voxelSniperConfiguration = new VoxelSniperConfiguration(getConfig());

        Bukkit.getPluginManager().registerEvents(this.voxelSniperListener, this);
        getLogger().info("Registered Sniper Listener.");

        // Initialize commands
        VoxelCommandManager.initialize(this);
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
        String version = "V" + Bukkit.getBukkitVersion().substring(0, 4).replace('.' , '_');
        return Version.valueOf(version);
    }

    @Override
    public VoxelSniperConfiguration getVoxelSniperConfiguration() {
        return voxelSniperConfiguration;
    }

    @Override
    public IFileHandler getFileHandler() {
        return fileHandler;
    }
}
