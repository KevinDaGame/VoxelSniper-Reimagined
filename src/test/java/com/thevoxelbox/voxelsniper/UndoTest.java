package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.snipe.Undo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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
        undo = new Undo();
    }

    @Test
    public void testUndoEmpty() {
        Assert.assertEquals(0, undo.getSize());
    }

    @Test
    public void testGetSize() {
        World world = Mockito.mock(World.class);
        for (int i = 0; i < 5; i++)
        {
            Location location = new Location(world, 0, 0, i);
            Block block = TestUtil.mockBlock(location, Material.DIRT);

            undo.put(block);
        }
        Assert.assertEquals(5, undo.getSize());
        Location location = new Location(world, 0, 0, 8);
        Block block = TestUtil.mockBlock(location, Material.DIRT);
        undo.put(block);
        Assert.assertEquals(6, undo.getSize());
        undo.put(block);
        Assert.assertEquals(6, undo.getSize());

    }

    @Test
    public void testPut() {
        World world = Mockito.mock(World.class);
        Location location = new Location(world, 0, 0, 0);
        Block block = TestUtil.mockBlock(location, Material.DIRT);

        undo.put(block);
        Assert.assertEquals(1, undo.getSize());
    }

    @Test
    public void testUndo() {
        World world = Mockito.mock(World.class);

        Location normalBlockLocation = new Location(world, 0, 0, 0);
        Block normalBlock = TestUtil.mockBlock(normalBlockLocation, Material.STONE);
        BlockState normalBlockState = normalBlock.getState();

        Location fragileBlockLocation = new Location(world, 0, 0, 1);
        Block fragileBlock = TestUtil.mockBlock(fragileBlockLocation, Material.TORCH);
        BlockState fragileBlockState = fragileBlock.getState();

        Location waterBlockLocation = new Location(world, 0, 0, 2);
        Block waterBlock = TestUtil.mockBlock(waterBlockLocation, Material.WATER);
        BlockState waterBlockState = waterBlock.getState();


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
