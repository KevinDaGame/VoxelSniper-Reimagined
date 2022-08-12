package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Version;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.LocationFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 *
 */
public class UndoTest
{
    private Undo undo;

    @Before
    public void setUp() {
        var main = Mockito.mock(IVoxelsniper.class);
        Mockito.when(main.getEnvironment()).thenReturn(Environment.BUKKIT);
        Mockito.when(main.getVersion()).thenReturn(Version.V1_16);
        VoxelSniper.voxelsniper = main;
        undo = new Undo();
    }

    private IBlock mockBlock(ILocation loc, VoxelMaterial t) {
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

    @Test
    public void testUndoEmpty() {
        Assert.assertEquals(0, undo.getSize());
    }

    @Test
    public void testGetSize() {
        IWorld world = Mockito.mock(BukkitWorld.class);
        for (int i = 0; i < 5; i++)
        {
            ILocation location = LocationFactory.getLocation(world, 0, 0, i);
            IBlock block = mockBlock(location, VoxelMaterial.DIRT);

            undo.put(block);
        }
        Assert.assertEquals(5, undo.getSize());
        ILocation location = LocationFactory.getLocation(world, 0, 0, 8);
        IBlock block = mockBlock(location, VoxelMaterial.DIRT);
        undo.put(block);
        Assert.assertEquals(6, undo.getSize());
        undo.put(block);
        Assert.assertEquals(6, undo.getSize());

    }

    @Test
    public void testPut() {
        IWorld world = Mockito.mock(BukkitWorld.class);
        ILocation location = LocationFactory.getLocation(world, 0, 0, 0);
        IBlock block = mockBlock(location, VoxelMaterial.DIRT);

        undo.put(block);
        Assert.assertEquals(1, undo.getSize());
    }

    @Test
    public void testUndo() {
        IWorld world = Mockito.mock(BukkitWorld.class);

        ILocation normalBlockLocation = LocationFactory.getLocation(world, 0, 0, 0);
        IBlock normalBlock = mockBlock(normalBlockLocation, VoxelMaterial.STONE);
        IBlockState normalBlockState = normalBlock.getState();

        ILocation fragileBlockLocation = LocationFactory.getLocation(world, 0, 0, 1);
        IBlock fragileBlock = mockBlock(fragileBlockLocation, VoxelMaterial.TORCH);
        IBlockState fragileBlockState = fragileBlock.getState();

        ILocation waterBlockLocation = LocationFactory.getLocation(world, 0, 0, 2);
        IBlock waterBlock = mockBlock(waterBlockLocation, VoxelMaterial.WATER);
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
