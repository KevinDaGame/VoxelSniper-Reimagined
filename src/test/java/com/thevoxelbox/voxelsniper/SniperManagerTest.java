package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.location.LocationFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.MaterialFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.player.BukkitPlayer;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 *
 */
public class SniperManagerTest {

    private VoxelProfileManager sniperManager;
    private UUID uuid;
    private Player bukkitPlayer;
    private BukkitPlayer absplayer;

    @Before
    public void setUp() {
        uuid = UUID.randomUUID();
        bukkitPlayer = Mockito.mock(Player.class);
        Mockito.when(bukkitPlayer.hasPermission(Mockito.any(String.class))).thenReturn(true);
        Mockito.when(bukkitPlayer.getUniqueId()).thenReturn(uuid);
        absplayer = new BukkitPlayer(bukkitPlayer);
        var main = Mockito.mock(IVoxelsniper.class);
        Mockito.when(main.getEnvironment()).thenReturn(Environment.BUKKIT);
        Mockito.when(main.getPlayer(uuid)).thenReturn(absplayer);

        LocationFactory.main = main;
        MaterialFactory.main = main;
        VoxelProfileManager.initialize(main);
        sniperManager = new VoxelProfileManager();

    }

    @Test
    public void testGetSniperForPlayer() {
        try (MockedStatic<Bukkit> bukkit = Mockito.mockStatic(Bukkit.class)) {
            bukkit.when(() -> Bukkit.getPlayer(uuid)).thenReturn(bukkitPlayer);
            Assert.assertEquals(absplayer, new BukkitPlayer(Bukkit.getPlayer(uuid)));
            Sniper sniper = sniperManager.getSniperForPlayer(absplayer);

            Assert.assertSame(absplayer, sniper.getPlayer());
            Assert.assertSame(sniper, sniperManager.getSniperForPlayer(absplayer));
        }
    }
}
