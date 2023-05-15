package com.github.kevindagame.voxelsniper.fileHandler;

import com.github.kevindagame.VoxelSniper;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class YamlConfiguration extends ConfigurationSection {

    public YamlConfiguration(ClassLoader loader, String fileName) {
        super(loadConfiguration(loader, fileName));
    }

    public YamlConfiguration(File file) {
        super(loadConfiguration(file));
    }

    @NotNull
    private static Map<String, Object> loadConfiguration(ClassLoader loader, String fileName) {
        Map<String, Object> contents = null;
        try (InputStream inputStream = loader.getResourceAsStream(fileName)) {
            if (inputStream != null) {
                contents = getYaml().load(inputStream);
            } else {
                VoxelSniper.voxelsniper.getLogger().warning("The resource " + fileName + " could not be found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents != null ? contents : new HashMap<>();
    }

    @NotNull
    private static Map<String, Object> loadConfiguration(File file) {
        Map<String, Object> contents = null;
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            contents = getYaml().load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents != null ? contents : new HashMap<>();
    }

    private static Yaml getYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setProcessComments(true);
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setSplitLines(false);
        dumperOptions.setProcessComments(true);
        return new Yaml(new Constructor(), new Representer(), dumperOptions, loaderOptions, new Resolver());
    }

    public String getString(String path) {
        return getString(path, null);
    }

    public String getString(String path, String defaultValue) {
        // head:a.b.c
        Object head = contents;
        String[] steps = path.split("\\.");
        for (String step : steps) {
            if (head instanceof Map) {
                head = ((Map<?, ?>) head).get(step);
            } else {
                return defaultValue;
            }
        }
        return (head instanceof String) ? (String) head : defaultValue;
    }

    public int getInt(String path, int defaultValue) {
        // head:a.b.c
        Object head = contents;
        String[] steps = path.split("\\.");
        for (String step : steps) {
            if (head instanceof Map) {
                head = ((Map<?, ?>) head).get(step);
            } else {
                return defaultValue;
            }
        }
        return (head instanceof Number) ? ((Number) head).intValue() : defaultValue;
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        // head:a.b.c
        Object head = contents;
        String[] steps = path.split("\\.");
        for (String step : steps) {
            if (head instanceof Map) {
                head = ((Map<?, ?>) head).get(step);
            } else {
                return defaultValue;
            }
        }
        return (head instanceof Boolean) ? (Boolean) head : defaultValue;
    }

    public void set(String name, Object value) {
        // head:a.b.c
        Object head = contents;
        String[] steps = name.split("\\.");
        if (steps.length < 1) return;
        for (int i = 0; i < (steps.length - 1); i++) {
            if (head instanceof Map) {
                head = ((Map<?, ?>) head).get(steps[i]);
            } else {
                return;
            }
        }
        if (head instanceof Map) {
            ((Map) head).put(steps[steps.length - 1], value);
        }

    }

    public void save(File langFile) throws IOException {
        try (FileWriter writer = new FileWriter(langFile)) {
            getYaml().dump(this.contents, writer);
        }
    }
}
