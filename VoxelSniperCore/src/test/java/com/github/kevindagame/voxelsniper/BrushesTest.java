package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.brush.BrushBuilder;
import com.github.kevindagame.brush.BrushData;
import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.brush.SnipeBrush;
import com.github.kevindagame.brush.perform.Performer;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.voxelsniper.biome.VoxelBiome;
import com.github.kevindagame.voxelsniper.permissions.Permission;
import com.github.kevindagame.voxelsniper.permissions.PermissionLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

/**
 *
 */
public class BrushesTest {

    private VoxelBrushManager brushes;

    @Before
    public void setUp() {
        var main = Mockito.mock(IVoxelsniper.class);
        Mockito.when(main.getBiome("minecraft", "plains")).thenReturn(new VoxelBiome("minecraft", "plains"));
        VoxelSniper.voxelsniper = main;
        brushes = new VoxelBrushManager();
    }

    @Test
    public void testBrushesEmpty() {
        Assert.assertEquals(0, brushes.registeredSniperBrushes());
        Assert.assertEquals(0, brushes.registeredSniperBrushHandles());
    }

    @Test
    public void testGetBrushForHandle() {
        var brushData = new BrushBuilder().name("mock").alias("mockhandle", "testhandle").setSupplier(SnipeBrush::new).build();
        brushes.registerSniperBrush(brushData);
        Assert.assertEquals(brushData, brushes.getBrushForHandle("mockhandle"));
        Assert.assertEquals(brushData, brushes.getBrushForHandle("testhandle"));
        Assert.assertNull(brushes.getBrushForHandle("notExistant"));
    }

    @Test
    public void testGetBrushForHandle_empty_handle() {
        var brushData = new BrushBuilder().name("mock").alias("mockhandle", "testhandle").setSupplier(SnipeBrush::new).build();
        brushes.registerSniperBrush(brushData);
        Assert.assertNull(brushes.getBrushForHandle(""));
        Assert.assertNull(brushes.getBrushForHandle(null));
    }

    @Test
    public void testRegisteredSniperBrushes() {
        brushes.registerSniperBrush(new BrushBuilder().name("mock").alias("mockhandle", "testhandle").setSupplier(SnipeBrush::new).build());
        Assert.assertEquals(2, brushes.registeredSniperBrushHandles());
    }

    @Test
    public void testRegisteredSniperBrushHandles() {
        brushes.registerSniperBrush(new BrushBuilder().name("mock").alias("mockhandle", "testhandle").setSupplier(SnipeBrush::new).build());
        Assert.assertEquals(2, brushes.registeredSniperBrushHandles());
    }

    @Test
    public void testGetSniperBrushHandles() {
        var brushData = new BrushBuilder().name("mock").alias("mockhandle", "testhandle").setSupplier(SnipeBrush::new).build();
        brushes.registerSniperBrush(brushData);
        Set<String> sniperBrushHandles = brushes.getSniperBrushHandles(brushData);
        Assert.assertTrue(sniperBrushHandles.contains("mockhandle"));
        Assert.assertTrue(sniperBrushHandles.contains("testhandle"));
        Assert.assertFalse(sniperBrushHandles.contains("notInSet"));
    }

    @Test
    public void testGetRegisteredBrushesMap() {
        var brushData = new BrushBuilder().name("mock").alias("mockhandle", "testhandle").setSupplier(SnipeBrush::new).build();
        brushes.registerSniperBrush(brushData);
        Map<String, BrushData> registeredBrushesMap = brushes.getRegisteredBrushesMap();
        Assert.assertTrue(registeredBrushesMap.containsValue(brushData));
        Assert.assertSame(registeredBrushesMap.get("mockhandle"), brushData);
        Assert.assertSame(registeredBrushesMap.get("testhandle"), brushData);
        Assert.assertNotSame(registeredBrushesMap.get("notAnEntry"), brushData);
    }

    @Test
    public void testPerformerBrushesArgumentsOverloading() {
        // Load all brushes
        brushes = VoxelBrushManager.initialize();

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("======================================================================");
        System.out.println("PERFORMER ARGUMENTS TEST");
        System.out.println("HINT A:     If this test fails, you need go to registerArguments where the class is failing, and add super.registerArguments() into your own arguments list.");
        System.out.println("EXAMPLE:    arguments.addAll(super.registerArguments());");
        System.out.println();
        System.out.println("HINT Z:     If this fails, your own argument is overriding the performer arguments. Please rename your arguments to something else other than \"p\".");
        System.out.println("======================================================================");

        for (String brushHandle : brushes.getBrushHandles()) {
            BrushData brushData = brushes.getBrushForHandle(brushHandle);
            IBrush brush = brushData.getSupplier().get();

            if (brush instanceof PerformerBrush) {
                List<String> arguments = brush.registerArguments();
                Assert.assertTrue("PERFORMER ARGUMENTS TEST: Please see the HINT A above. Failing at: " + brushData.getName(), arguments.contains("p"));

                Assert.assertEquals("PERFORMER ARGUMENTS TEST: Duplicate argument 'p'. Please see the HINT Z above. Failing at: " + brushData.getName(), 1, Collections.frequency(arguments, "p"));
            }
        }
        System.out.println("Performer Arguments Test OK!");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
    }

    @Test
    public void testPerformerBrushesArgumentValuesOverloading() {
        // Load all brushes
        brushes = VoxelBrushManager.initialize();
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("======================================================================");
        System.out.println("PERFORMER ARGUMENTS VALUES TEST");
        System.out.println("HINT A:     If this fails, you need go to registerArgumentValues where the class is failing, and add super.registerArgumentValues() into your own arguments map.");
        System.out.println("EXAMPLE:    argumentValues.putAll(super.registerArgumentValues());");
        System.out.println();
        System.out.println("HINT Z:     If this fails, your own argument values are overriding the performer argument values. Please rename your arguments to something else other than \"p\".");
        System.out.println("======================================================================");

        Collection<String> performerHandles = Performer.getPerformerHandles();

        for (String brushHandle : brushes.getBrushHandles()) {
            BrushData brushData = brushes.getBrushForHandle(brushHandle);
            IBrush brush = brushData.getSupplier().get();

            if (brush instanceof PerformerBrush) {
                HashMap<String, List<String>> argumentValues = brush.registerArgumentValues();
                Assert.assertTrue("PERFORMER ARGUMENTS VALUES TEST: Please see the HINT A above. Failing at: " + brushData.getName(), argumentValues.containsKey("p"));
                Assert.assertTrue("PERFORMER ARGUMENTS VALUES TEST: Please see the HINT Z above. Failing at: " + brushData.getName(), argumentValues.get("p").containsAll(performerHandles));
            }
        }
        System.out.println("Performer Arguments VALUES Test OK!");
    }

    @Test
    public void testBrushPermissions() {
        // Load all brushes and permissions
        VoxelBrushManager brushes = VoxelBrushManager.initialize();
        PermissionLoader permissions = PermissionLoader.getInstance();
        permissions.load();

        // check that every brush has a permission and that permission is registered in the permissions.yml file
        for (Map.Entry<String, BrushData> e : brushes.getRegisteredBrushesMap().entrySet()) {
            BrushData brushData = e.getValue();
            Assert.assertNotNull("Brush permission must not be null (brush: " + brushData.getName() + ")", brushData.getPermission());
            Permission permissionNode = permissions.getPermission(brushData.getPermission());
            Assert.assertNotNull("Brush permission must be in permissions.yml (brush: " + brushData.getName() + ")", permissionNode);
        }
    }
}
