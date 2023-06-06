package com.github.kevindagame.voxelsniper.permissions;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

public class SpigotPermissionManager {

    public static void register() {
        PermissionLoader loader = PermissionLoader.getInstance();
        loader.load();

        PluginManager plugins = Bukkit.getPluginManager();

        loader.getAllPermissions().stream().map(SpigotPermissionManager::createNode).forEach(plugins::addPermission);

    }

    private static org.bukkit.permissions.Permission createNode(Permission permission) {
        Map<String, Boolean> children = new HashMap<>();
        for (Permission p : permission.getChildren()) {
            children.put(p.getName(), true);
        }
        return new org.bukkit.permissions.Permission(permission.getName(), permission.getDescription(), PermissionDefault.OP, children);
    }
}
