package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Jockey_Brush
 *
 * @author Voxel
 * @author Monofraps
 */
public class JockeyBrush extends Brush {

    private static final int ENTITY_STACK_LIMIT = 50;
    private JockeyType jockeyType = JockeyType.NORMAL_ALL_ENTITIES;
    private Entity jockeyedEntity = null;

    private boolean playerOnly = false;

    /**
     *
     */
    public JockeyBrush() {
        this.setName("Jockey");
    }

    private void sitOn(final SnipeData v) {
        final Chunk targetChunk = this.getWorld().getChunkAt(this.getTargetBlock().getLocation());
        final int targetChunkX = targetChunk.getX();
        final int targetChunkZ = targetChunk.getZ();

        double range = Double.MAX_VALUE;
        Entity closest = null;

        for (int x = targetChunkX - 1; x <= targetChunkX + 1; x++) {
            for (int y = targetChunkZ - 1; y <= targetChunkZ + 1; y++) {
                for (final Entity entity : this.getWorld().getChunkAt(x, y).getEntities()) {
                    if (entity.getEntityId() == v.owner().getPlayer().getEntityId()) {
                        continue;
                    }

                    if (jockeyType == JockeyType.NORMAL_PLAYER_ONLY || jockeyType == JockeyType.INVERSE_PLAYER_ONLY) {
                        if (!(entity instanceof Player)) {
                            continue;
                        }
                    }

                    final Location entityLocation = entity.getLocation();
                    final double entityDistance = entityLocation.distance(v.owner().getPlayer().getLocation());

                    if (entityDistance < range) {
                        range = entityDistance;
                        closest = entity;
                    }
                }
            }
        }

        if (closest != null) {
            final Player player = v.owner().getPlayer();
            final PlayerTeleportEvent playerTeleportEvent = new PlayerTeleportEvent(player, player.getLocation(), closest.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

            Bukkit.getPluginManager().callEvent(playerTeleportEvent);

            if (!playerTeleportEvent.isCancelled()) {
                if (jockeyType == JockeyType.INVERSE_PLAYER_ONLY || jockeyType == JockeyType.INVERSE_ALL_ENTITIES) {
                    player.addPassenger(closest);
                } else {
                    closest.addPassenger(player);
                    jockeyedEntity = closest;
                }
                v.sendMessage(ChatColor.GOLD + "You are now sitting on the most nearby entity!");
            }
        } else {
            v.sendMessage(ChatColor.RED + "Could not find any " + (playerOnly ? "players" : "entities") + " to sit on :(");
        }
    }

    private void stack(final SnipeData v) {
        final int brushSizeDoubled = v.getBrushSize() * 2;

        List<Entity> nearbyEntities = v.owner().getPlayer().getNearbyEntities(brushSizeDoubled, brushSizeDoubled, brushSizeDoubled);
        Entity lastEntity = v.owner().getPlayer();
        int stackHeight = 0;

        for (Entity entity : nearbyEntities) {
            if (!(stackHeight >= ENTITY_STACK_LIMIT)) {
                if (jockeyType == JockeyType.STACK_ALL_ENTITIES) {
                    lastEntity.addPassenger(entity);
                    lastEntity = entity;
                    stackHeight++;
                } else if (jockeyType == JockeyType.STACK_PLAYER_ONLY) {
                    if (entity instanceof Player) {
                        lastEntity.addPassenger(entity);
                        lastEntity = entity;
                        stackHeight++;
                    }
                } else {
                    v.owner().getPlayer().sendMessage("You broke the stack! :O");
                }
            } else {
                return;
            }
        }

    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (jockeyType == JockeyType.STACK_ALL_ENTITIES || jockeyType == JockeyType.STACK_PLAYER_ONLY) {
            stack(v);
        } else {
            this.sitOn(v);
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (jockeyType == JockeyType.INVERSE_PLAYER_ONLY || jockeyType == JockeyType.INVERSE_ALL_ENTITIES) {
            v.owner().getPlayer().eject();
            v.owner().getPlayer().sendMessage(ChatColor.GOLD + "The entity on top of you has been ejected!");
        } else {
            if (jockeyedEntity != null) {
                jockeyedEntity.eject();
                jockeyedEntity = null;
                v.owner().getPlayer().sendMessage(ChatColor.GOLD + "You have been ejected!");
            }
        }

    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom("Current Jockey Mode: " + ChatColor.GREEN + jockeyType.toString());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.JOCKEY_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("player")) {
            this.playerOnly = !this.playerOnly;

            if (playerOnly) {
                jockeyType = JockeyType.valueOf(this.jockeyType.name().split("_")[0] + "_PLAYER_ONLY");
            } else {
                jockeyType = JockeyType.valueOf(this.jockeyType.name().split("_")[0] + "_ALL_ENTITIES");
            }
            v.sendMessage(ChatColor.GREEN + "Now targeting " + (this.playerOnly ? "players only." : "all entities."));
            return;
        }

        try {
            if (params[0].equalsIgnoreCase("inverse")) {
                if (playerOnly) {
                    jockeyType = JockeyType.INVERSE_PLAYER_ONLY;
                } else {
                    jockeyType = JockeyType.INVERSE_ALL_ENTITIES;
                }
                return;
            }
            if (params[0].equalsIgnoreCase("stack")) {
                if (playerOnly) {
                    jockeyType = JockeyType.STACK_PLAYER_ONLY;
                } else {
                    jockeyType = JockeyType.STACK_ALL_ENTITIES;
                }
            }
            if (params[0].equalsIgnoreCase("normal")) {
                if (playerOnly) {
                    jockeyType = JockeyType.NORMAL_PLAYER_ONLY;
                } else {
                    jockeyType = JockeyType.NORMAL_ALL_ENTITIES;
                }
            }
            v.sendMessage("Current Jockey Mode: " + ChatColor.GREEN + jockeyType.toString());
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("inverse", "stack", "normal", "player"));
    }

    /**
     * Available types of jockey modes.
     */
    private enum JockeyType {
        NORMAL_ALL_ENTITIES("Normal (All)"),
        NORMAL_PLAYER_ONLY("Normal (Player only)"),
        INVERSE_ALL_ENTITIES("Inverse (All)"),
        INVERSE_PLAYER_ONLY("Inverse (Player only)"),
        STACK_ALL_ENTITIES("Stack (All)"),
        STACK_PLAYER_ONLY("Stack (Player only)");

        private final String name;

        JockeyType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.jockey";
    }
}
