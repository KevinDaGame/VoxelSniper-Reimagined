package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.Sniper;

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

    @Before
    public void setUp() {
        sniperManager = new VoxelProfileManager();
    }

    @Test
    public void testGetSniperForPlayer() {
        UUID uuid = UUID.randomUUID();
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getUniqueId())
                .thenReturn(uuid);

        try (MockedStatic<Bukkit> bukkit = Mockito.mockStatic(Bukkit.class)) {
            bukkit.when(() -> Bukkit.getPlayer(uuid)).thenReturn(player);
            Assert.assertSame(Bukkit.getPlayer(uuid), player);

            Sniper sniper = sniperManager.getSniperForPlayer(player);
            Assert.assertSame(player, sniper.getPlayer());
            Assert.assertSame(sniper, sniperManager.getSniperForPlayer(player));
        }
    }
}
