package com.thevoxelbox.voxelsniper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.mockito.Mockito;

public class TestUtil {
    static Block mockBlock(Location loc, Material t) {
        Block block = Mockito.mock(Block.class);
        BlockState normalBlockState = Mockito.mock(BlockState.class);
        Mockito.when(block.getLocation())
                .thenReturn(loc);
        Mockito.when(block.getState())
                .thenReturn(normalBlockState);
        Mockito.when(block.getType())
                .thenReturn(t);

        Mockito.when(normalBlockState.getLocation())
                .thenReturn(loc);
        Mockito.when(normalBlockState.getBlock())
                .thenReturn(block);

        return block;
    }
}
