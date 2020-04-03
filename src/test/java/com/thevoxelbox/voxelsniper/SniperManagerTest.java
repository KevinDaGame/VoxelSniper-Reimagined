package com.thevoxelbox.voxelsniper;

import org.junit.Before;
import org.mockito.Mockito;

/**
 *
 */
public class SniperManagerTest
{
    private VoxelProfileManager sniperManager;

    @Before
    public void setUp() throws Exception
    {
        sniperManager = new VoxelProfileManager(Mockito.mock(VoxelSniper.class));
    }

}
