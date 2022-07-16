package com.thevoxelbox.voxelsniper.bukkit;

import com.google.common.collect.Maps;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.player.AbstractPlayer;

import java.util.Map;
import java.util.UUID;

/**
 * Profile manager for Sniper instances. Each SniperProfile object represents a single player.
 */
public class VoxelProfileManager {

    private static IVoxelsniper main;
    private static VoxelProfileManager instance = null;

    private final Map<UUID, Sniper> sniperInstances = Maps.newHashMap();

    public static VoxelProfileManager getInstance() {
        return instance;
    }

    public static void initialize(IVoxelsniper main) {
        VoxelProfileManager.main = main;
        VoxelProfileManager profileManager = getInstance();

        if (profileManager == null) {
            instance = new VoxelProfileManager();
            profileManager = getInstance();
        }
    }

    public Sniper getSniperForPlayer(AbstractPlayer player) {
        if (sniperInstances.get(player.getUniqueId()) == null) {
            sniperInstances.put(player.getUniqueId(), new Sniper(main, player));
        }
        return sniperInstances.get(player.getUniqueId());
    }
}
