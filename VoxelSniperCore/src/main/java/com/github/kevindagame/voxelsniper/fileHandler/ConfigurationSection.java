package com.github.kevindagame.voxelsniper.fileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConfigurationSection {
    protected final Map<String, Object> contents;

    public ConfigurationSection(Map<String, Object> contents) {
        this.contents = contents;
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

    @Nullable
    public String getString(String path) {
        return getString(path, null);
    }

    public String getString(String path, String def) {
        // head:a.b.c
        Object head = contents;
        String[] steps = path.split("\\.");
        for (String step : steps) {
            if (head instanceof Map) {
                head = ((Map<?, ?>) head).get(step);
            } else {
                return def;
            }
        }
        return (head instanceof String) ? (String) head : def;
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

    @NotNull
    public List<String> getStringList(String path) {
        var lst = getList(path);
        return lst.stream().filter(this::isPrimitiveWrapperOrString).map(String::valueOf).collect(Collectors.toCollection(ArrayList::new));
    }

    @NotNull
    public List<ConfigurationSection> getSectionList(String path) {
        var lst = getList(path);
        List<ConfigurationSection> res = new ArrayList<>();
        for (Object o : lst) {
            if (o instanceof Map<?, ?> m) {
                res.add(new ConfigurationSection((Map<String, Object>) m));
            }
        }
        return res;
    }

    @NotNull
    private List<?> getList(String path) {
        // head:a.b.c
        Object head = contents;
        String[] steps = path.split("\\.");
        for (String step : steps) {
            if (head instanceof Map) {
                head = ((Map<?, ?>) head).get(step);
            } else {
                return new ArrayList<>();
            }
        }
        if (head instanceof List<?> lst) {
            return lst;
        } else {
            return new ArrayList<>();
        }
    }

    private boolean isPrimitiveWrapperOrString(@Nullable Object input) {
        return input instanceof Integer || input instanceof Boolean
                || input instanceof Character || input instanceof Byte
                || input instanceof Short || input instanceof Double
                || input instanceof Long || input instanceof Float || input instanceof String;
    }
}
