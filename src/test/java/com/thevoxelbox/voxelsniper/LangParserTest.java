package com.thevoxelbox.voxelsniper;


import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.Messages0;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class LangParserTest {

    private static final boolean run = true;
    @Before
    public void setUp() {

    }

    @Test
    public void testBrushesEmpty() {
        if (!run) return;
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.options().width(500);

        Class<Messages0> cls = Messages0.class;
        for (Field field : cls.getDeclaredFields()) {
            String name = field.getName();
            System.out.println(name+',');
            try {
                String value = (String) field.get(null);
                String encoded = MiniMessage.miniMessage().serialize(LegacyComponentSerializer.legacySection().deserialize(value.replace("\n", "\\n")));
                yaml.set(name, encoded);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            yaml.save("lang.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
