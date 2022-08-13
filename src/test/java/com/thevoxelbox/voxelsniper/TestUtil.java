package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import org.mockito.Mockito;

public class TestUtil {
    public static IBlock mockBlock(ILocation loc, VoxelMaterial t) {
        IBlock block = Mockito.mock(IBlock.class);
        IBlockState normalBlockState = Mockito.mock(IBlockState.class);
        Mockito.when(block.getLocation())
                .thenReturn(loc);
        Mockito.when(block.getState())
                .thenReturn(normalBlockState);
        Mockito.when(block.getMaterial())
                .thenReturn(t);

        Mockito.when(normalBlockState.getLocation())
                .thenReturn(loc);
        Mockito.when(normalBlockState.getBlock())
                .thenReturn(block);

        return block;
    }
}
