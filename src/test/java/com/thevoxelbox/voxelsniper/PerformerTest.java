package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.brush.perform.pMaterial;
import com.thevoxelbox.voxelsniper.brush.perform.vPerformer;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class PerformerTest {
    @Test
    public void testBasePerformer() {
        vPerformer p = new vPerformer() {
            @Override
            public void info(VoxelMessage vm) {}
            @Override
            public void init(SnipeData v) {}
            @Override
            public void perform(Block b) {}
        };

        Assert.assertNull(p.getUndo());

        p.setUndo();
        Assert.assertNotNull(p.getUndo());  // getUndo() resets undo to null
        Assert.assertNull(p.getUndo());

        Assert.assertFalse(p.isUsingReplaceMaterial()); // default behaviour
    }

    @Test
    public void testMatPerformer() {
        try (MockedStatic<Bukkit> bukkit = Mockito.mockStatic(Bukkit.class)) {
            // mock calls for Bukkit.createBlockData
            BlockData diaBlockData = Mockito.mock(BlockData.class);
            Mockito.when(diaBlockData.getMaterial()).thenReturn(Material.DIAMOND_BLOCK);
            bukkit.when(() -> Bukkit.createBlockData(Material.DIAMOND_BLOCK)).thenReturn(diaBlockData);
            BlockData airBlockData = Mockito.mock(BlockData.class);
            Mockito.when(airBlockData.getMaterial()).thenReturn(Material.AIR);
            bukkit.when(() -> Bukkit.createBlockData(Material.AIR)).thenReturn(airBlockData);

            // init Performer
            SnipeData snipeData = Mockito.mock(SnipeData.class);
            Mockito.when(snipeData.getVoxelMaterial()).thenReturn(Material.DIAMOND_BLOCK);
            pMaterial p = new pMaterial();

            // test p.init()
            p.init(snipeData);
            Mockito.verify(snipeData).getVoxelMaterial();

            // test p.info(vm)
            VoxelMessage vm = Mockito.mock(VoxelMessage.class);
            p.info(vm);
            InOrder order = Mockito.inOrder(vm);
            order.verify(vm).performerName("Material");
            order.verify(vm).voxel();

            // test p.isUsingReplaceMaterial()
            Assert.assertFalse(p.isUsingReplaceMaterial());

            // test perform() - change block
            World world = Mockito.mock(World.class);
            Location loc = new Location(world, 100, 50, 200);
            Block b = TestUtil.mockBlock(loc, Material.AIR);

            p.setUndo();
            p.perform(b);
            Mockito.verify(b).setBlockData(diaBlockData);
            Assert.assertSame(p.getUndo().getSize(), 1);

            // test perform() noop, b.getType() == voxelMaterial
            Block b2 = TestUtil.mockBlock(loc, Material.DIAMOND_BLOCK);

            p.setUndo();
            p.perform(b2);
            Mockito.verify(b2, Mockito.never()).setBlockData(Mockito.any());
            Assert.assertSame(p.getUndo().getSize(), 0);
        }
    }

}
