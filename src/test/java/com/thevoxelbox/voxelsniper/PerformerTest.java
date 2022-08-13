package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.brush.perform.pMaterial;
import com.thevoxelbox.voxelsniper.brush.perform.vPerformer;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.Environment;
import com.thevoxelbox.voxelsniper.voxelsniper.IVoxelsniper;
import com.thevoxelbox.voxelsniper.voxelsniper.Version;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.LocationFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.MaterialFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class PerformerTest {

    @Before
    public void setUp() {
        var main = Mockito.mock(IVoxelsniper.class);
        Mockito.when(main.getEnvironment()).thenReturn(Environment.BUKKIT);
        Mockito.when(main.getVersion()).thenReturn(Version.V1_16);
        VoxelSniper.voxelsniper = main;
    }

    @Test
    public void testBasePerformer() {
        vPerformer p = new vPerformer() {
            @Override
            public void info(VoxelMessage vm) {}
            @Override
            public void init(SnipeData v) {}
            @Override
            public void perform(IBlock b) {}
        };

        Assert.assertNull(p.getUndo());

        p.setUndo();
        Assert.assertNotNull(p.getUndo());  // getUndo() resets undo to null
        Assert.assertNull(p.getUndo());

        Assert.assertFalse(p.isUsingReplaceMaterial()); // default behaviour
    }

    @Test
    public void testMatPerformer() {
        try (MockedStatic<MaterialFactory> factory = Mockito.mockStatic(MaterialFactory.class)) {
            // mock calls for MaterialFactory.getMaterial
            IBlockData diaBlockData = Mockito.mock(IBlockData.class);
            Mockito.when(diaBlockData.getMaterial()).thenReturn(VoxelMaterial.DIAMOND_BLOCK);
            IMaterial diaMat = Mockito.mock(IMaterial.class);
            Mockito.when(diaMat.createBlockData()).thenReturn(diaBlockData);
            factory.when(() -> MaterialFactory.getMaterial(VoxelMaterial.DIAMOND_BLOCK)).thenReturn(diaMat);
            IBlockData airBlockData = Mockito.mock(IBlockData.class);
            Mockito.when(airBlockData.getMaterial()).thenReturn(VoxelMaterial.AIR);
            IMaterial airMat = Mockito.mock(IMaterial.class);
            Mockito.when(airMat.createBlockData()).thenReturn(airBlockData);
            factory.when(() -> MaterialFactory.getMaterial(VoxelMaterial.AIR)).thenReturn(airMat);

            // init Performer
            IWorld world = Mockito.mock(BukkitWorld.class);
            SnipeData snipeData = Mockito.mock(SnipeData.class);
            Mockito.when(snipeData.getVoxelMaterial()).thenReturn(VoxelMaterial.DIAMOND_BLOCK);
            Mockito.when(snipeData.getWorld()).thenReturn(world);
            pMaterial p = new pMaterial();

            // test p.init()
            p.init(snipeData);
            Mockito.verify(snipeData).getVoxelMaterial();
            try {
                Assert.assertSame(p.getClass().getDeclaredField("voxelMaterial").get(p), VoxelMaterial.DIAMOND_BLOCK);
                Assert.assertSame(p.getClass().getSuperclass().getDeclaredField("w").get(p), world);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }


            // test p.info(vm)
            VoxelMessage vm = Mockito.mock(VoxelMessage.class);
            p.info(vm);
            InOrder order = Mockito.inOrder(vm);
            order.verify(vm).performerName("Material");
            order.verify(vm).voxel();

            // test p.isUsingReplaceMaterial()
            Assert.assertFalse(p.isUsingReplaceMaterial());

            // test perform() - change block
            ILocation loc = LocationFactory.getLocation(world, 100, 50, 200);
            IBlock b = TestUtil.mockBlock(loc, VoxelMaterial.AIR);

            p.setUndo();
            p.perform(b);
            Mockito.verify(b).setBlockData(diaBlockData);
            Assert.assertSame(p.getUndo().getSize(), 1);

            // test perform() noop, b.getType() == voxelMaterial
            IBlock b2 = TestUtil.mockBlock(loc, VoxelMaterial.DIAMOND_BLOCK);

            p.setUndo();
            p.perform(b2);
            Mockito.verify(b2, Mockito.never()).setBlockData(Mockito.any());
            Assert.assertSame(p.getUndo().getSize(), 0);
        }
    }

}
