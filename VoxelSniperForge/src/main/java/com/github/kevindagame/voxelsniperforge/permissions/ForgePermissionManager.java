package com.github.kevindagame.voxelsniperforge.permissions;

import com.github.kevindagame.voxelsniper.permissions.Permission;
import com.github.kevindagame.voxelsniper.permissions.PermissionLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.network.chat.Component;
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
        var split = permission.getName().split("\\.");
        PermissionNode<Boolean> res;
        if (split.length > 1) {
            var remaining = permission.getName().substring((split[0] + ".").length());
            res = new PermissionNode<>(split[0], remaining, PermissionTypes.BOOLEAN, defaultResolver);
        } else {
            res = new PermissionNode<>(PERMISSION_PREFIX, split[0], PermissionTypes.BOOLEAN, defaultResolver);
        }

        var name = Component.literal(permission.getName());
        var desc = Component.literal(permission.getDescription() != null ? permission.getDescription() : "");
        res.setInformation(name, desc);
        return res;
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
            if (permission.getParent() != null) {
                var parentNode = getPermissionNode(permission.getParent().getName());
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
