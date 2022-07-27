package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.Sniper;

import java.util.UUID;

import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.player.AbstractPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.player.BukkitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 *
 */
public class SniperManagerTest {

    private VoxelProfileManager sniperManager;

    @Before
    public void setUp() {
        VoxelProfileManager.initialize(Mockito.mock(IVoxelsniper.class));
        sniperManager = new VoxelProfileManager();

    }

    @Test
    public void testGetSniperForPlayer() {
        UUID uuid = UUID.randomUUID();
        Player player = Mockito.mock(Player.class);
        AbstractPlayer absplayer = new BukkitPlayer(player);
        Mockito.when(player.getUniqueId())
                .thenReturn(uuid);

        try (MockedStatic<Bukkit> bukkit = Mockito.mockStatic(Bukkit.class)) {
            bukkit.when(() -> Bukkit.getPlayer(uuid)).thenReturn(player);
            Assert.assertEquals(absplayer, new BukkitPlayer(Bukkit.getPlayer(uuid)));
            Sniper sniper = sniperManager.getSniperForPlayer(absplayer);

            Assert.assertSame(absplayer, sniper.getPlayer());
            Assert.assertSame(sniper, sniperManager.getSniperForPlayer(absplayer));
        }
    }
}
