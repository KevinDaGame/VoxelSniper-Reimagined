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

    public void save(File langFile) throws IOException {
        try (FileWriter writer = new FileWriter(langFile)) {
            getYaml().dump(this.contents, writer);
        }
    }
}
