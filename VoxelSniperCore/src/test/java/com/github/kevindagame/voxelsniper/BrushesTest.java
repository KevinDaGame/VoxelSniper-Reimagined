package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.brush.perform.Performer;
import com.github.kevindagame.brush.perform.PerformerBrush;
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
        Mockito.when(main.getEnvironment()).thenReturn(Environment.SPIGOT);
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
        IBrush brush = Mockito.mock(IBrush.class);
        brushes.registerSniperBrush(brush.getClass(), "mockhandle", "testhandle");
        Assert.assertEquals(brush.getClass(), brushes.getBrushForHandle("mockhandle"));
        Assert.assertEquals(brush.getClass(), brushes.getBrushForHandle("testhandle"));
        Assert.assertNull(brushes.getBrushForHandle("notExistant"));
    }

    @Test
    public void testRegisteredSniperBrushes() {
        IBrush brush = Mockito.mock(IBrush.class);
        brushes.registerSniperBrush(brush.getClass(), "mockhandle", "testhandle");
        Assert.assertEquals(2, brushes.registeredSniperBrushHandles());
    }

    @Test
    public void testRegisteredSniperBrushHandles() {
        IBrush brush = Mockito.mock(IBrush.class);
        brushes.registerSniperBrush(brush.getClass(), "mockhandle", "testhandle");
        Assert.assertEquals(2, brushes.registeredSniperBrushHandles());
    }

    @Test
    public void testGetSniperBrushHandles() {
        IBrush brush = Mockito.mock(IBrush.class);
        brushes.registerSniperBrush(brush.getClass(), "mockhandle", "testhandle");
        Set<String> sniperBrushHandles = brushes.getSniperBrushHandles(brush.getClass());
        Assert.assertTrue(sniperBrushHandles.contains("mockhandle"));
        Assert.assertTrue(sniperBrushHandles.contains("testhandle"));
        Assert.assertFalse(sniperBrushHandles.contains("notInSet"));
    }

    @Test
    public void testGetRegisteredBrushesMap() {
        IBrush brush = Mockito.mock(IBrush.class);
        brushes.registerSniperBrush(brush.getClass(), "mockhandle", "testhandle");
        Map<String, Class<? extends IBrush>> registeredBrushesMap = brushes.getRegisteredBrushesMap();
        Assert.assertTrue(registeredBrushesMap.containsValue(brush.getClass()));
        Assert.assertFalse(registeredBrushesMap.containsValue(IBrush.class));
        Assert.assertSame(registeredBrushesMap.get("mockhandle"), brush.getClass());
        Assert.assertSame(registeredBrushesMap.get("testhandle"), brush.getClass());
        Assert.assertNotSame(registeredBrushesMap.get("notAnEntry"), brush.getClass());
    }

    @Test
    public void testPerformerBrushesArgumentsOverloading() throws Exception {
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
            Class<? extends IBrush> clazz = brushes.getBrushForHandle(brushHandle);
            IBrush brush = clazz.getDeclaredConstructor().newInstance();

            if (brush instanceof PerformerBrush) {
                List<String> arguments = brush.registerArguments();
                Assert.assertTrue("PERFORMER ARGUMENTS TEST: Please see the HINT A above. Failing at: " + clazz.getName(), arguments.contains("p"));

                Assert.assertEquals("PERFORMER ARGUMENTS TEST: Duplicate argument 'p'. Please see the HINT Z above. Failing at: " + clazz.getName(), 1, Collections.frequency(arguments, "p"));
            }
        }
        System.out.println("Performer Arguments Test OK!");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
    }

    @Test
    public void testPerformerBrushesArgumentValuesOverloading() throws Exception {
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
            Class<? extends IBrush> clazz = brushes.getBrushForHandle(brushHandle);
            IBrush brush = clazz.getDeclaredConstructor().newInstance();

            if (brush instanceof PerformerBrush) {
                HashMap<String, List<String>> argumentValues = brush.registerArgumentValues();
                Assert.assertTrue("PERFORMER ARGUMENTS VALUES TEST: Please see the HINT A above. Failing at: " + clazz.getName(), argumentValues.containsKey("p"));
                Assert.assertTrue("PERFORMER ARGUMENTS VALUES TEST: Please see the HINT Z above. Failing at: " + clazz.getName(), argumentValues.get("p").containsAll(performerHandles));
            }
        }
        System.out.println("Performer Arguments VALUES Test OK!");
    }
}
