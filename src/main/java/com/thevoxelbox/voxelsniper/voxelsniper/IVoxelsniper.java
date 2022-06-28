package com.thevoxelbox.voxelsniper.voxelsniper;

import com.thevoxelbox.voxelsniper.voxelsniper.player.IPlayer;

import java.util.UUID;

public interface IVoxelsniper {
    IPlayer getPlayer(UUID uuid);
    Environment getEnvironment();
    Version getVersion();
}
