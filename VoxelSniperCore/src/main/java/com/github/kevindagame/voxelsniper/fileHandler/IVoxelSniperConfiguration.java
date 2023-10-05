package com.github.kevindagame.voxelsniper.fileHandler;

public interface IVoxelSniperConfiguration {
    String CONFIG_IDENTIFIER_UNDO_CACHE_SIZE = "undo-cache-size";
    String CONFIG_IDENTIFIER_MAX_BRUSH_SIZE = "max-brush-size";
    String CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED = "message-on-login-enabled";
    String CONFIG_IDENTIFIER_DEFAULT_BRUSH = "default-brush";
    String CONFIG_IDENTIFIER_PLOTSQUARED_INTEGRATION_ENABLED = "plotsquared-integration-enabled";
    String CONFIG_IDENTIFIER_WORLDGUARD_INTEGRATION_ENABLED = "worldguard-integration-enabled";
    String CONFIG_IDENTIFIER_UPDATE_CHECKER_ENABLED = "update-checker-enabled";
    int DEFAULT_UNDO_CACHE_SIZE = 20;
    int DEFAULT_MAX_BRUSH_SIZE = 20;
    boolean DEFAULT_MESSAGE_ON_LOGIN_ENABLED = true;

    int getUndoCacheSize();

    void setUndoCacheSize(int size);

    int getMaxBrushSize();

    boolean isMessageOnLoginEnabled();

    void setMessageOnLoginEnabled(boolean enabled);

    String getDefaultBrush();

    boolean isPlotSquaredIntegrationEnabled();

    boolean isWorldGuardIntegrationEnabled();

    boolean isUpdateCheckerEnabled();
}
