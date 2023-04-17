package com.github.kevindagame.voxelsniper.fileHandler;

import com.github.kevindagame.voxelsniper.IVoxelsniper;

import java.io.File;

/**
 * Configuration storage defining global configurations for VoxelSniper.
 */
public class VoxelSniperConfiguration {

    public static final String CONFIG_IDENTIFIER_UNDO_CACHE_SIZE = "undo-cache-size";
    public static final String CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED = "message-on-login-enabled";
    public static final String CONFIG_IDENTIFIER_DEFAULT_BRUSH = "default-brush";
    public static final String CONFIG_IDENTIFIER_PLOTSQUARED_INTEGRATION_ENABLED = "plotsquared-integration-enabled";
    public static final String CONFIG_IDENTIFIER_WORLDGUARD_INTEGRATION_ENABLED = "worldguard-integration-enabled";
    public static final String CONFIG_IDENTIFIER_UPDATE_CHECKER_ENABLED = "update-checker-enabled";

    public static final int DEFAULT_UNDO_CACHE_SIZE = 20;
    public static final boolean DEFAULT_MESSAGE_ON_LOGIN_ENABLED = true;
    private static final boolean DEFAULT_USE_PLOTSQUARED = false;
    private static final boolean DEFAULT_USE_WORLDGUARD = false;
    private static final boolean DEFAULT_USE_UPDATE_CHECKER = true;
    private final YamlConfiguration configuration;

    /**
     * @param voxelSniper {@link IVoxelsniper} reference
     */
    public VoxelSniperConfiguration(IVoxelsniper voxelSniper) {
        this.configuration = new YamlConfiguration(new File(voxelSniper.getFileHandler().getDataFolder(), "config.yml"));
    }

    /**
     * Returns the maximum amount of snipes stored in the undo cache of snipers.
     *
     * @return the maximum amount of snipes stored in the undo cache of snipers
     */
    public int getUndoCacheSize() {
        return configuration.getInt(CONFIG_IDENTIFIER_UNDO_CACHE_SIZE, DEFAULT_UNDO_CACHE_SIZE);
    }

    /**
     * Set the maximum amount of snipes stored in the undo cache of snipers.
     *
     * @param size size of undo cache
     */
    public void setUndoCacheSize(int size) {
        configuration.set(CONFIG_IDENTIFIER_UNDO_CACHE_SIZE, size);
    }

    /**
     * Returns if the login message is enabled.
     *
     * @return true if message on login is enabled, false otherwise.
     */
    public boolean isMessageOnLoginEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED, DEFAULT_MESSAGE_ON_LOGIN_ENABLED);
    }

    /**
     * Set the message on login to be enabled or disabled.
     *
     * @param enabled Message on Login enabled
     */
    public void setMessageOnLoginEnabled(boolean enabled) {
        configuration.set(CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED, enabled);
    }

    /**
     * Returns the default brush.
     *
     * @return the default brush.
     */
    public String getDefaultBrush() {
        return configuration.getString(CONFIG_IDENTIFIER_DEFAULT_BRUSH);
    }

    /**
     * Returns if PlotSquared integration is enabled.
     *
     * @return whether PlotSquared integration is enabled
     */
    public boolean isPlotSquaredIntegrationEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_PLOTSQUARED_INTEGRATION_ENABLED, DEFAULT_USE_PLOTSQUARED);
    }

    /**
     * Returns if WorldGuard integration is enabled.
     *
     * @return whether WorldGuard integration is enabled
     */
    public boolean isWorldGuardIntegrationEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_WORLDGUARD_INTEGRATION_ENABLED, DEFAULT_USE_WORLDGUARD);
    }

    /**
     * Returns if the update checker is enabled.
     *
     * @return whether the update checker is enabled
     */
    public boolean isUpdateCheckerEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_UPDATE_CHECKER_ENABLED, DEFAULT_USE_UPDATE_CHECKER);
    }
}
