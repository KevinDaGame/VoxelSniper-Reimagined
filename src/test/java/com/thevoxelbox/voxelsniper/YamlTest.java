package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.voxelsniper.fileHandler.YamlConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class YamlTest {
    private final File resourcesDirectory = new File("src/test/resources");

    @Test
    public void testYaml() {
        File f = new File(resourcesDirectory, "test.yml");
        YamlConfiguration config = new YamlConfiguration(f);

        Assert.assertNull(config.getString("nonexistent"));
        Assert.assertSame(42, config.getInt("nonexistent", 42));
        Assert.assertTrue(config.getBoolean("nonexistent", true));
        Assert.assertFalse(config.getBoolean("nonexistent", false));

        Assert.assertEquals("Hello World!", config.getString("test.testText"));
        Assert.assertSame(15, config.getInt("test.testInt", 0));
        Assert.assertFalse(config.getBoolean("test.bool.testFalse", true));
        Assert.assertTrue(config.getBoolean("test.bool.testTrue", false));
    }
}
