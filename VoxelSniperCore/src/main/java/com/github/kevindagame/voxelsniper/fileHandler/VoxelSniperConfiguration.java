package com.github.kevindagame.voxelsniper.fileHandler;

import com.github.kevindagame.voxelsniper.IVoxelsniper;

import java.io.File;

/**
 * Configuration storage defining global configurations for VoxelSniper.
 */
public class VoxelSniperConfiguration implements IVoxelSniperConfiguration {

    private static final boolean DEFAULT_USE_PLOTSQUARED = false;
    private static final boolean DEFAULT_USE_WORLDGUARD = false;
    private static final boolean DEFAULT_USE_UPDATE_CHECKER = true;
    private static final String DEFAULT_BRUSH = "snipe";
    private final YamlConfiguration configuration;

    /**
     * @param voxelSniper {@link IVoxelsniper} reference
     */
    public VoxelSniperConfiguration(IVoxelsniper voxelSniper) {
        var file = new File(voxelSniper.getFileHandler().getDataFolder(), "config.yml");
        if (!file.exists()) {
            voxelSniper.getFileHandler().saveResource(VoxelSniperConfiguration.class.getClassLoader(), "config.yml", false);
        }
        this.configuration = new YamlConfiguration(file);
    }

    /**
     * Returns the maximum amount of snipes stored in the undo cache of snipers.
     *
     * @return the maximum amount of snipes stored in the undo cache of snipers
     */
    @Override
    public int getUndoCacheSize() {
        return configuration.getInt(CONFIG_IDENTIFIER_UNDO_CACHE_SIZE, DEFAULT_UNDO_CACHE_SIZE);
    }

    /**
     * Set the maximum amount of snipes stored in the undo cache of snipers.
     *
     * @param size size of undo cache
     */
    @Override
    public void setUndoCacheSize(int size) {
        configuration.set(CONFIG_IDENTIFIER_UNDO_CACHE_SIZE, size);
    }

    /**
     * Returns the maximum brush size.
     *
     * @return the maximum brush size
     */
    @Override
    public int getMaxBrushSize() {
        return configuration.getInt(CONFIG_IDENTIFIER_MAX_BRUSH_SIZE, DEFAULT_MAX_BRUSH_SIZE);
    }

    /**
     * Returns if the login message is enabled.
     *
     * @return true if message on login is enabled, false otherwise.
     */
    @Override
    public boolean isMessageOnLoginEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED, DEFAULT_MESSAGE_ON_LOGIN_ENABLED);
    }

    /**
     * Set the message on login to be enabled or disabled.
     *
     * @param enabled Message on Login enabled
     */
    @Override
    public void setMessageOnLoginEnabled(boolean enabled) {
        configuration.set(CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED, enabled);
    }

    /**
     * Returns the default brush.
     *
     * @return the default brush.
     */
    @Override
    public String getDefaultBrush() {
        return configuration.getString(CONFIG_IDENTIFIER_DEFAULT_BRUSH, DEFAULT_BRUSH);
    }

    /**
     * Returns if PlotSquared integration is enabled.
     *
     * @return whether PlotSquared integration is enabled
     */
    @Override
    public boolean isPlotSquaredIntegrationEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_PLOTSQUARED_INTEGRATION_ENABLED, DEFAULT_USE_PLOTSQUARED);
    }

    /**
     * Returns if WorldGuard integration is enabled.
     *
     * @return whether WorldGuard integration is enabled
     */
    @Override
    public boolean isWorldGuardIntegrationEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_WORLDGUARD_INTEGRATION_ENABLED, DEFAULT_USE_WORLDGUARD);
    }

    /**
     * Returns if the update checker is enabled.
     *
     * @return whether the update checker is enabled
     */
    @Override
    public boolean isUpdateCheckerEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_UPDATE_CHECKER_ENABLED, DEFAULT_USE_UPDATE_CHECKER);
    }
}
