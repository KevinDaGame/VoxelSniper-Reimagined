package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.MaterialFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.IPlayer;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

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
        VoxelSniper.voxelsniper = main;
        VoxelProfileManager.initialize();

    }

    @Test
    public void testGetSniperForPlayer() {
        try (MockedStatic<MaterialFactory> factory = Mockito.mockStatic(MaterialFactory.class)) {
            IBlockData airBlockData = Mockito.mock(IBlockData.class);
            Mockito.when(airBlockData.getMaterial()).thenReturn(VoxelMaterial.AIR);
            IMaterial mat = Mockito.mock(IMaterial.class);
            Mockito.when(mat.createBlockData()).thenReturn(airBlockData);
            factory.when(() -> MaterialFactory.getMaterial(VoxelMaterial.AIR)).thenReturn(mat);

            Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(absplayer);

            Assert.assertSame(absplayer, sniper.getPlayer());
            Assert.assertSame(sniper, VoxelProfileManager.getInstance().getSniperForPlayer(absplayer));
        }
    }
}
