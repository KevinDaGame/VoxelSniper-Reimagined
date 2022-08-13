package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.player.AbstractPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.player.BukkitPlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Jockey_Brush
 *
 * @author Voxel
 * @author Monofraps
 */
public class JockeyBrush extends Brush {

    private static final int ENTITY_STACK_LIMIT = 50;
    private JockeyType jockeyType = JockeyType.NORMAL_ALL_ENTITIES;
    private IEntity jockeyedEntity = null;

    private boolean playerOnly = false;

    /**
     *
     */
    public JockeyBrush() {
        this.setName("Jockey");
    }

    private void sitOn(final SnipeData v) {
        final IChunk targetChunk = this.getWorld().getChunkAtLocation(this.getTargetBlock().getLocation());
        final int targetChunkX = targetChunk.getX();
        final int targetChunkZ = targetChunk.getZ();

        double range = Double.MAX_VALUE;
        IEntity closest = null;

        for (int x = targetChunkX - 1; x <= targetChunkX + 1; x++) {
            for (int y = targetChunkZ - 1; y <= targetChunkZ + 1; y++) {
                for (final IEntity entity : this.getWorld().getChunkAtLocation(x, y).getEntities()) {
                    if (entity.getEntityId() == v.owner().getPlayer().getEntityId()) {
                        continue;
                    }

                    if (jockeyType == JockeyType.NORMAL_PLAYER_ONLY || jockeyType == JockeyType.INVERSE_PLAYER_ONLY) {
                        if (!(entity instanceof Player)) {
                            continue;
                        }
                    }

                    final ILocation entityLocation = entity.getLocation();
                    final double entityDistance = entityLocation.distance(v.owner().getPlayer().getLocation());

                    if (entityDistance < range) {
                        range = entityDistance;
                        closest = entity;
                    }
                }
            }
        }

        if (closest != null) {
            final AbstractPlayer player = v.owner().getPlayer();
            final PlayerTeleportEvent playerTeleportEvent = new PlayerTeleportEvent(((BukkitPlayer)player).getPlayer(), ((BukkitLocation)player.getLocation()).getLocation(), ((BukkitLocation)closest.getLocation()).getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

            Bukkit.getPluginManager().callEvent(playerTeleportEvent);

            if (!playerTeleportEvent.isCancelled()) {
                if (jockeyType == JockeyType.INVERSE_PLAYER_ONLY || jockeyType == JockeyType.INVERSE_ALL_ENTITIES) {
                    player.addPassenger(closest);
                } else {
                    closest.addPassenger(player);
                    jockeyedEntity = closest;
                }
                v.sendMessage(Messages.SITTING_ON_CLOSEST_ENTITY);
            }
        } else {
            if (playerOnly) {
                v.sendMessage(Messages.NO_PLAYERS_FOUND_TO_SIT_ON);
            } else {
                v.sendMessage(Messages.NO_ENTITIES_FOUND_TO_SIT_ON);
            }
        }
    }

    private void stack(final SnipeData v) {
        final int brushSizeDoubled = v.getBrushSize() * 2;

        List<IEntity> nearbyEntities = v.owner().getPlayer().getNearbyEntities(brushSizeDoubled, brushSizeDoubled, brushSizeDoubled);
        IEntity lastEntity = v.owner().getPlayer();
        int stackHeight = 0;

        for (IEntity entity : nearbyEntities) {
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
                    v.sendMessage(Messages.YOU_BROKE_THE_STACK);
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
            v.sendMessage(Messages.ENTITY_EJECTED);
        } else {
            if (jockeyedEntity != null) {
                jockeyedEntity.eject();
                jockeyedEntity = null;
                v.sendMessage(Messages.YOU_HAVE_BEEN_EJECTED);
            }
        }

    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.CURRENT_JOCKEY_MODE.replace("%mode%",String.valueOf(jockeyType.toString())));
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
            if (playerOnly) {
                v.sendMessage(Messages.NO_PLAYERS_FOUND_TO_SIT_ON);
            } else {
                v.sendMessage(Messages.NO_ENTITIES_FOUND_TO_SIT_ON);
            }
            if (playerOnly) {
                v.sendMessage(Messages.JOCKEY_TARGETING_PLAYERS);
            } else {
                v.sendMessage(Messages.JOCKEY_TARGETING_ENTITIES);
            }
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
                    v.sendMessage(Messages.JOCKEY_TARGETING_PLAYERS);
                } else {
                    v.sendMessage(Messages.JOCKEY_TARGETING_ENTITIES);
                }
            }
            v.sendMessage(Messages.CURRENT_JOCKEY_MODE.replace("%mode%", jockeyType.toString()));
        } catch (ArrayIndexOutOfBoundsException temp) {
            temp.printStackTrace();
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
