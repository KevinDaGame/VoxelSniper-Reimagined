package com.thevoxelbox.voxelsniper;

import com.google.common.collect.Multimap;
import com.thevoxelbox.voxelsniper.brush.IBrush;
import com.thevoxelbox.voxelsniper.brush.perform.Performer;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Set;
import static org.hamcrest.CoreMatchers.hasItems;

/**
 *
 */
public class BrushesTest {

    private VoxelBrushManager brushes;
    private VoxelCommandManager commands;

    @Before
    public void setUp() {
        brushes = new VoxelBrushManager();
    }

    @Test
    public void testRegisterSniperBrush() {
        IBrush brush = Mockito.mock(IBrush.class);
        brushes.registerSniperBrush(brush.getClass(), "mockhandle", "testhandle");
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
        Assert.assertEquals(0, brushes.registeredSniperBrushes());
        IBrush brush = Mockito.mock(IBrush.class);
        brushes.registerSniperBrush(brush.getClass(), "mockhandle", "testhandle");
        Assert.assertEquals(1, brushes.registeredSniperBrushes());
    }

    @Test
    public void testRegisteredSniperBrushHandles() {
        Assert.assertEquals(0, brushes.registeredSniperBrushHandles());
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
    public void testGetRegisteredBrushesMultimap() {
        IBrush brush = Mockito.mock(IBrush.class);
        brushes.registerSniperBrush(brush.getClass(), "mockhandle", "testhandle");
        Multimap<Class<? extends IBrush>, String> registeredBrushesMultimap = brushes.getRegisteredBrushesMultimap();
        Assert.assertTrue(registeredBrushesMultimap.containsKey(brush.getClass()));
        Assert.assertFalse(registeredBrushesMultimap.containsKey(IBrush.class));
        Assert.assertTrue(registeredBrushesMultimap.containsEntry(brush.getClass(), "mockhandle"));
        Assert.assertTrue(registeredBrushesMultimap.containsEntry(brush.getClass(), "testhandle"));
        Assert.assertFalse(registeredBrushesMultimap.containsEntry(brush.getClass(), "notAnEntry"));
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
        // Unload and revert.
        brushes = new VoxelBrushManager();
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
        String[] performerHandlesArray = performerHandles.toArray(new String[0]);

        for (String brushHandle : brushes.getBrushHandles()) {
            Class<? extends IBrush> clazz = brushes.getBrushForHandle(brushHandle);
            IBrush brush = clazz.getDeclaredConstructor().newInstance();

            if (brush instanceof PerformerBrush) {
                HashMap<String, List<String>> argumentValues = brush.registerArgumentValues();
                Assert.assertTrue("PERFORMER ARGUMENTS VALUES TEST: Please see the HINT A above. Failing at: " + clazz.getName(), argumentValues.containsKey("p"));
                Assert.assertThat("PERFORMER ARGUMENTS VALUES TEST: Please see the HINT Z above. Failing at: " + clazz.getName(), argumentValues.get("p"), hasItems(performerHandlesArray));
            }
        }
        System.out.println("Performer Arguments VALUES Test OK!");
        // Unload and revert.
        brushes = new VoxelBrushManager();
    }
}
