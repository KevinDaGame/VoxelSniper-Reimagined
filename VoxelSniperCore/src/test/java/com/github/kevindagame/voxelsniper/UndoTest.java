package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
import com.github.kevindagame.voxelsniper.world.IWorld;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 *
 */
public class UndoTest {
    private Undo undo;

    @Before
    public void setUp() {
        var main = Mockito.mock(IVoxelsniper.class);
        Mockito.when(main.getEnvironment()).thenReturn(Environment.SPIGOT);
        Mockito.when(main.getVersion()).thenReturn(Version.V1_16);
        VoxelSniper.voxelsniper = main;
        undo = new Undo();
    }

    private IBlock mockBlock(BaseLocation loc, VoxelMaterialType t) {
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
        Mockito.when(normalBlockState.getMaterial())
                .thenReturn(t);

        return block;
    }

    @Test
    public void testUndoEmpty() {
        Assert.assertEquals(0, undo.getSize());
    }

    @Test
    public void testGetSize() {
        IWorld world = Mockito.mock(IWorld.class);
        for (int i = 0; i < 5; i++) {
            BaseLocation location = new BaseLocation(world, 0, 0, i);
            IBlock block = mockBlock(location, VoxelMaterialType.DIRT);

            undo.put(block);
        }
        Assert.assertEquals(5, undo.getSize());
        BaseLocation location = new BaseLocation(world, 0, 0, 8);
        IBlock block = mockBlock(location, VoxelMaterialType.DIRT);
        undo.put(block);
        Assert.assertEquals(6, undo.getSize());
        undo.put(block);
        Assert.assertEquals(6, undo.getSize());

    }

    @Test
    public void testPut() {
        IWorld world = Mockito.mock(IWorld.class);
        BaseLocation location = new BaseLocation(world, 0, 0, 0);
        IBlock block = mockBlock(location, VoxelMaterialType.DIRT);

        undo.put(block);
        Assert.assertEquals(1, undo.getSize());
    }

    @Test
    public void testUndo() {
        IWorld world = Mockito.mock(IWorld.class);

        BaseLocation normalBlockLocation = new BaseLocation(world, 0, 0, 0);
        IBlock normalBlock = mockBlock(normalBlockLocation, VoxelMaterialType.STONE);
        IBlockState normalBlockState = normalBlock.getState();

        BaseLocation fragileBlockLocation = new BaseLocation(world, 0, 0, 1);
        IBlock fragileBlock = mockBlock(fragileBlockLocation, VoxelMaterialType.TORCH);
        IBlockState fragileBlockState = fragileBlock.getState();

        BaseLocation waterBlockLocation = new BaseLocation(world, 0, 0, 2);
        IBlock waterBlock = mockBlock(waterBlockLocation, VoxelMaterialType.WATER);
        IBlockState waterBlockState = waterBlock.getState();


        undo.put(waterBlock);
        undo.put(fragileBlock);
        undo.put(normalBlock);
        undo.undo();

        InOrder inOrder = Mockito.inOrder(normalBlockState, waterBlockState, fragileBlockState);
        // first stone, then torch, then water
        inOrder.verify(normalBlockState).update(true, false);
        inOrder.verify(fragileBlockState).update(true, false);
        inOrder.verify(waterBlockState).update(true, false);
    }
}
