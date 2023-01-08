package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class BlockWrapperTest extends TestCase {

    public void testTestClone() {
        var blockdata = Mockito.mock(IBlockData.class);
        when(blockdata.getMaterial()).thenReturn(Mockito.mock(VoxelMaterial.class));
        var mock = Mockito.mock(IBlock.class);
        when(mock.getBlockData()).thenReturn(blockdata);

        BlockWrapper bw = new BlockWrapper(mock);
        var clone = bw.clone();
        assert clone.getBlockData() != bw.getBlockData(); // assert that it was cloned deeply
        assertEquals(bw.getX(), clone.getX());
        assertEquals(bw.getY(), clone.getY());
        assertEquals(bw.getZ(), clone.getZ());
        assertEquals(bw.getWorld(), clone.getWorld());
    }
}