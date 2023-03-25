package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.material.IMaterial;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

/**
 *
 */
public class SniperManagerTest {

    private IPlayer absplayer;

    @Before
    public void setUp() {
        UUID uuid = UUID.randomUUID();
        absplayer = Mockito.mock(IPlayer.class);
        Mockito.when(absplayer.getUniqueId()).thenReturn(uuid);
        Mockito.when(absplayer.hasPermission(Mockito.any(String.class))).thenReturn(true);

        var main = Mockito.mock(IVoxelsniper.class);
        Mockito.when(main.getPlayer(uuid)).thenReturn(absplayer);

        IBlockData airBlockData = Mockito.mock(IBlockData.class);
        Mockito.when(airBlockData.getMaterial()).thenReturn(VoxelMaterialType.AIR);
        IMaterial mat = Mockito.mock(IMaterial.class);
        Mockito.when(mat.createBlockData()).thenReturn(airBlockData);

        Mockito.when(main.getMaterial(VoxelMaterialType.AIR)).thenReturn(mat);

        VoxelSniper.voxelsniper = main;

        Sniper sniper = new Sniper(absplayer);
        Mockito.when(absplayer.getSniper()).thenReturn(sniper);
    }

    @Test
    public void testGetSniperForPlayer() {
        Sniper sniper = absplayer.getSniper();

        Assert.assertSame(absplayer, sniper.getPlayer());
        Assert.assertSame(sniper, absplayer.getSniper());
    }
}
