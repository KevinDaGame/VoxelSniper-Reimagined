package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.voxelsniper.fileHandler.ConfigurationSection;
import com.github.kevindagame.voxelsniper.fileHandler.YamlConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YamlTest {
    private final File resourcesDirectory = new File("src/test/resources");

    @Test
    public void testYaml() {
        File f = new File(resourcesDirectory, "test.yml");
        YamlConfiguration config = new YamlConfiguration(f);

        Assert.assertNull(config.getString("nonexistent"));
        Assert.assertEquals("default3763654", config.getString("nonexistent.test", "default3763654"));
        Assert.assertEquals(config.getString("nonexistent", "Hello World!"), "Hello World!");
        Assert.assertSame(42, config.getInt("nonexistent", 42));
        Assert.assertTrue(config.getBoolean("nonexistent", true));
        Assert.assertFalse(config.getBoolean("nonexistent", false));

        Assert.assertEquals("Hello World!", config.getString("test.testText"));
        Assert.assertSame(15, config.getInt("test.testInt", 0));
        Assert.assertFalse(config.getBoolean("test.bool.testFalse", true));
        Assert.assertTrue(config.getBoolean("test.bool.testTrue", false));

        {
            ArrayList<String> lst = new ArrayList<>();
            lst.add("test");
            lst.add("test5");
            lst.add("test42");
            lst.add("5");
            Assert.assertEquals(lst, config.getStringList("test.testList"));
        }

        {
            List<ConfigurationSection> lst = config.getSectionList("test.testObjectList");
            Assert.assertSame(3, lst.size());

            Assert.assertNotNull(lst.get(0));
            Assert.assertNotNull(lst.get(1));
            Assert.assertNotNull(lst.get(2));

            Assert.assertEquals("Hello World!", lst.get(0).getString("name"));
            Assert.assertEquals("testDesc", lst.get(0).getString("description"));

            Assert.assertEquals("Hello World!", lst.get(1).getString("name"));
            Assert.assertEquals("testDesc2", lst.get(1).getString("description"));

            Assert.assertEquals("third", lst.get(2).getString("name"));
            Assert.assertNull(lst.get(2).getString("description"));
        }
        // test set (don't save)
        Assert.assertSame(0, config.getInt("a.b.c.nonexistent", 0));
        config.set("a.b.c.nonexistent", 1);
        Assert.assertSame(1, config.getInt("a.b.c.nonexistent", 0));

        Assert.assertSame(0, config.getInt("nonexistent", 0));
        config.set("nonexistent", 13);
        Assert.assertSame(13, config.getInt("nonexistent", 0));
    }
}
