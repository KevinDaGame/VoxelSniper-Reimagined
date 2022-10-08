package com.github.kevindagame.voxelsniper.permissions;

import com.github.kevindagame.voxelsniper.fileHandler.YamlConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

public final class PermissionLoader {
    private static final Map<String, Permission> permissionMap = new HashMap<>();
    private static PermissionLoader instance;
    private boolean loaded = false;

    private PermissionLoader() {}

    public static PermissionLoader getInstance() {
        if (PermissionLoader.instance == null)
            PermissionLoader.instance = new PermissionLoader();
        return instance;
    }

    public void addPermission(Permission perm) {
        permissionMap.put(perm.name(), perm);
    }

    @Nullable
    public Permission getPermission(String name) {
        return permissionMap.get(name);
    }

    public List<Permission> getAllPermissions() {
        return permissionMap.values().stream().toList();
    }

    public void load() {
        if (loaded) return;
        YamlConfiguration yaml = new YamlConfiguration(PermissionLoader.class.getClassLoader(), "permissions.yml");

        var data = yaml.getStringList("permissions");
        data.forEach(this::addPermissionIfNotExists);
        loaded = true;
    }

    private Permission addPermissionIfNotExists(final String name) {
        var countPoints = name.split("\\.").length - 1;
        String nameWithoutWildcard = name;
        if (countPoints > 1 && name.endsWith(".*")) {
            // remove wildcard at end
            nameWithoutWildcard = name.substring(0, name.length() - 2);
        }
        if (countPoints > 0 && !name.endsWith(".*")) {
            // remove last part of the permission
            String wildcard = nameWithoutWildcard.substring(0, name.lastIndexOf('.')) + ".*";
            // we recursively generate the wildcard permissions
            Permission parent = addPermissionIfNotExists(wildcard);
            return addPermissionIfNotExists(name, parent);
        }
        return addPermissionIfNotExists(name, null);
    }

    private Permission addPermissionIfNotExists(String name, Permission parent) {
        Permission permission = getPermission(name);
        if (permission == null) {
            permission = new Permission(name, parent);
            addPermission(permission);
        }
        return permission;
    }
}
