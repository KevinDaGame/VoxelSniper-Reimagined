package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
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
        Mockito.when(main.getMaterial(Mockito.anyString(), Mockito.anyString())).thenReturn(Mockito.mock(VoxelMaterial.class));
        VoxelSniper.voxelsniper = main;
        undo = new Undo();
    }

    private IBlock mockBlock(BaseLocation loc, VoxelMaterial t) {
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
            IBlock block = mockBlock(location, VoxelMaterial.STONE());

            undo.put(block);
        }
        Assert.assertEquals(5, undo.getSize());
        BaseLocation location = new BaseLocation(world, 0, 0, 8);
        IBlock block = mockBlock(location, VoxelMaterial.STONE());
        undo.put(block);
        Assert.assertEquals(6, undo.getSize());
        undo.put(block);
        Assert.assertEquals(6, undo.getSize());

    }

    @Test
    public void testPut() {
        IWorld world = Mockito.mock(IWorld.class);
        BaseLocation location = new BaseLocation(world, 0, 0, 0);
        IBlock block = mockBlock(location, VoxelMaterial.STONE());

        undo.put(block);
        Assert.assertEquals(1, undo.getSize());
    }

    @Test
    public void testUndo() {
        IWorld world = Mockito.mock(IWorld.class);

        BaseLocation normalBlockLocation = new BaseLocation(world, 0, 0, 0);
        IBlock normalBlock = mockBlock(normalBlockLocation, VoxelMaterial.STONE());
        IBlockState normalBlockState = normalBlock.getState();

        BaseLocation secondBlockLocation = new BaseLocation(world, 0, 0, 1);
        IBlock secondBlock = mockBlock(secondBlockLocation, VoxelMaterial.AIR());
        IBlockState secondBlockState = secondBlock.getState();


        undo.put(normalBlock);
        undo.put(secondBlock);
        undo.undo();

        InOrder inOrder = Mockito.inOrder(normalBlockState, secondBlockState);
        // first stone, then torch, then water
        inOrder.verify(normalBlockState).update(true, false);
        inOrder.verify(secondBlockState).update(true, false);
    }
}
