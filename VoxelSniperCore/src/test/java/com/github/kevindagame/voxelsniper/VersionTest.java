package com.github.kevindagame.voxelsniper;

import org.junit.Assert;
import org.junit.Test;

public class VersionTest {
    @Test
    public void testIsSupported() {
        Version server = Version.V1_16;
        Version block = Version.V1_17;
        Assert.assertFalse(server.isSupported(block));
        Assert.assertTrue(server.isSupported(server));
        Assert.assertTrue(Version.V1_17.isSupported(Version.V1_16));
    }
}