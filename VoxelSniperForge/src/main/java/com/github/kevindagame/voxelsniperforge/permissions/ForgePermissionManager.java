package com.github.kevindagame.voxelsniperforge.permissions;

import com.github.kevindagame.voxelsniper.permissions.Permission;
import com.github.kevindagame.voxelsniper.permissions.PermissionLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;

import org.jetbrains.annotations.Nullable;

public class ForgePermissionManager {

    private static final String PERMISSION_PREFIX = "voxelsniper";
    private static final Map<String, PermissionNode<Boolean>> permissionMap = new HashMap<>();

    private static PermissionNode<Boolean> createNode(Permission permission) {
        PermissionNode.PermissionResolver<Boolean> defaultResolver = new ForgePermissionResolver(permission);
        var split = permission.name().split("\\.");
        if (split.length > 1) {
            var remaining = permission.name().substring((split[0] + ".").length());
            return new PermissionNode<>(split[0], remaining, PermissionTypes.BOOLEAN, defaultResolver);
        }
        return new PermissionNode<>(PERMISSION_PREFIX, split[0], PermissionTypes.BOOLEAN, defaultResolver);
    }

    public static void register(PermissionGatherEvent.Nodes event) {
        PermissionLoader loader = PermissionLoader.getInstance();
        loader.load();

        loader.getAllPermissions().stream().map(ForgePermissionManager::createNode).forEach((n) -> {
            event.addNodes(n);
            permissionMap.put(n.getNodeName(), n);
        });

    }

    public static PermissionNode<Boolean> getPermissionNode(String name) {
        return permissionMap.get(name);
    }

    private record ForgePermissionResolver(Permission permission) implements PermissionNode.PermissionResolver<Boolean> {
        @Override
        public Boolean resolve(@Nullable ServerPlayer player, UUID playerUUID, PermissionDynamicContext<?>... context) {
            if (permission.parent() != null) {
                var parentNode = getPermissionNode(permission.parent().name());
                if (player != null) {
                    return PermissionAPI.getPermission(player, parentNode);
                }
                return PermissionAPI.getOfflinePermission(playerUUID, parentNode);
            }
            if (player != null) {
                return ServerLifecycleHooks.getCurrentServer().getPlayerList().isOp( player.getGameProfile());
            }
            return false; // TODO offline OP
        }
    }
}
