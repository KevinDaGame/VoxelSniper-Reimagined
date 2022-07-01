package com.thevoxelbox.voxelsniper.voxelsniper;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class VersionTest extends TestCase {
    @Test
    public void testIsSupported() {
        Version server = Version.V1_16;
        Version block = Version.V1_17;
        Assert.assertFalse(server.isSupported(block));
    }
}