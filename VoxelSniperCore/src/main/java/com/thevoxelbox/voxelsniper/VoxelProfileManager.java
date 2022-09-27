package com.thevoxelbox.voxelsniper;

import com.google.common.collect.Maps;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.IPlayer;

import java.util.Map;
import java.util.UUID;

/**
 * Profile manager for Sniper instances. Each SniperProfile object represents a single player.
 */
public class VoxelProfileManager {

    private static VoxelProfileManager instance = null;

    private final Map<UUID, Sniper> sniperInstances = Maps.newHashMap();

    public static VoxelProfileManager getInstance() {
        return instance;
    }

    public static void initialize() {
        VoxelProfileManager profileManager = getInstance();

        if (profileManager == null) {
            instance = new VoxelProfileManager();
        }
    }

    public Sniper getSniperForPlayer(IPlayer player) {
        if (sniperInstances.get(player.getUniqueId()) == null) {
            sniperInstances.put(player.getUniqueId(), new Sniper(player));
        }
        return sniperInstances.get(player.getUniqueId());
    }
}
