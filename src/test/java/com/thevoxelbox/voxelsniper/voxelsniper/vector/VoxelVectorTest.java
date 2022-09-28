package com.thevoxelbox.voxelsniper.voxelsniper.vector;

import junit.framework.TestCase;
import org.bukkit.util.Vector;

public class VoxelVectorTest extends TestCase {

    public void testAngle() {
        var vec1 = new Vector(10, 10, 10);
        var vecCompare1 = new Vector(10, 20, 20);
        var vec2 = new VoxelVector(10, 10, 10);
        var vecCompare2 = new VoxelVector(10, 20, 20);
        assertEquals(Math.round(vec1.angle(vecCompare1)), Math.round(vec2.angle(vecCompare2)));
    }

    public void testCrossProduct() {
        var vec1 = new Vector(10, 10, 10);
        var vecCompare1 = new Vector(10, 20, 20);
        var vec2 = new VoxelVector(10, 10, 10);
        var vecCompare2 = new VoxelVector(10, 20, 20);
        var res1 = vec1.crossProduct(vecCompare1);
        var res2 = vec2.crossProduct(vecCompare2);
        assertEquals(res1.getX(), res2.getX());
        assertEquals(res1.getY(), res2.getY());
        assertEquals(res1.getZ(), res2.getZ());

    }

    public void testSubtract() {
        var vec1 = new Vector(10, 10, 10);
        var vecCompare1 = new Vector(10, 20, 20);
        var vec2 = new VoxelVector(10, 10, 10);
        var vecCompare2 = new VoxelVector(10, 20, 20);
        var res1 = vec1.subtract(vecCompare1);
        var res2 = vec2.subtract(vecCompare2);
        assertEquals(res1.getX(), res2.getX());
        assertEquals(res1.getY(), res2.getY());
        assertEquals(res1.getZ(), res2.getZ());
    }
}