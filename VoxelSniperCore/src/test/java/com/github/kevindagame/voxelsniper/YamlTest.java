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

            Assert.assertTrue(lst.get(0) instanceof ConfigurationSection);
            Assert.assertTrue(lst.get(1) instanceof ConfigurationSection);
            Assert.assertTrue(lst.get(2) instanceof ConfigurationSection);

            Assert.assertEquals("Hello World!", lst.get(0).getString("name"));
            Assert.assertEquals("testDesc", lst.get(0).getString("description"));

            Assert.assertEquals("Hello World!", lst.get(1).getString("name"));
            Assert.assertEquals("testDesc2", lst.get(1).getString("description"));

            Assert.assertEquals("third", lst.get(2).getString("name"));
            Assert.assertNull(lst.get(2).getString("description"));
        }
    }
}
