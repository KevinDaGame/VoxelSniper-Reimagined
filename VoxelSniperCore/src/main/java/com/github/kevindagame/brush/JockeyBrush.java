package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.BrushOperation.CustomOperation;
import com.github.kevindagame.util.BrushOperation.CustomOperationContext;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Voxel
 * @author Monofraps
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#jockey-brush">...</a>
 */
public class JockeyBrush extends CustomBrush {

    private static final int ENTITY_STACK_LIMIT = 50;
    private JockeyType jockeyType = JockeyType.NORMAL;
    private IEntity jockeyedEntity = null;

    private boolean playerOnly = false;

    /**
     *
     */
    public JockeyBrush() {
        this.setName("Jockey");
    }

    private void sitOn(final SnipeData v) {
        BaseLocation location = this.getLastBlock().getLocation();
        IChunk targetChunk = location.getChunk();
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

                    if (this.playerOnly) {
                        if (!(entity instanceof IPlayer)) {
                            continue;
                        }
                    }

                    final double entityDistance = entity.getLocation().distance(location);
                    if (entityDistance < range) {
                        range = entityDistance;
                        closest = entity;
                    }
                }
            }
        }

        if (closest != null) {
            final IPlayer player = v.owner().getPlayer();

            if (jockeyType == JockeyType.INVERSE) {
                if (closest.teleport(player))
                    player.addPassenger(closest);
            } else {
                if (player.teleport(closest)) {
                    closest.addPassenger(player);
                    jockeyedEntity = closest;
                }
            }
            v.sendMessage(Messages.SITTING_ON_CLOSEST_ENTITY);
        } else {
            if (playerOnly) {
                v.sendMessage(Messages.NO_PLAYERS_FOUND_TO_SIT_ON);
            } else {
                v.sendMessage(Messages.NO_ENTITIES_FOUND_TO_SIT_ON);
            }
        }
    }

    private void stack(final SnipeData v) {
        final int brushSize = Math.max(1, v.getBrushSize());

        List<IEntity> nearbyEntities = this.getLastBlock().getNearbyEntities(brushSize, brushSize, brushSize);
        IPlayer player = v.owner().getPlayer();
        IEntity lastEntity = player;
        int stackHeight = 0;

        for (IEntity entity : nearbyEntities) {
            if (stackHeight >= ENTITY_STACK_LIMIT) {
                return;
            } else if (entity.getEntityId() != player.getEntityId()) {
                if (!(this.playerOnly && entity instanceof IPlayer)) {
                    if (jockeyType == JockeyType.STACK) {
                        lastEntity.addPassenger(entity);
                        lastEntity = entity;
                        stackHeight++;
                    } else {
                        v.sendMessage(Messages.YOU_BROKE_THE_STACK);
                    }
                }
            }
        }

    }

    @Override
    protected final void arrow(final SnipeData v) {
        getOperations().add(new CustomOperation(getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    protected final void powder(final SnipeData v) {
        getOperations().add(new CustomOperation(getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.CURRENT_JOCKEY_MODE.replace("%mode%", jockeyType.getName()));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.JOCKEY_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }
        try {
            if (params[0].equalsIgnoreCase("inverse")) {
                jockeyType = JockeyType.INVERSE;
            } else if (params[0].equalsIgnoreCase("stack")) {
                jockeyType = JockeyType.STACK;
            } else if (params[0].equalsIgnoreCase("normal")) {
                jockeyType = JockeyType.NORMAL;
            }
            v.sendMessage(Messages.CURRENT_JOCKEY_MODE.replace("%mode%", jockeyType.getName()));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("inverse", "stack", "normal"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.jockey";
    }

    @Override
    public boolean perform(@NotNull List<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        if (operations.size() != 1) return false;
        switch (Objects.requireNonNull(getSnipeAction())) {
            case GUNPOWDER:
                // invers || stack: remove passenger(s) from player
                // normal: remove player from pasenger (jockeyedEntity)
                if (jockeyType == JockeyType.INVERSE || jockeyType == JockeyType.STACK) {
                    Set<IEntity> foundPassengers = new HashSet<>();
                    foundPassengers.add(snipeData.owner().getPlayer());
                    while (foundPassengers.size() > 0) {
                        Set<IEntity> entities = foundPassengers;
                        foundPassengers = new HashSet<>();
                        for (IEntity e : entities) {
                            List<IEntity> passengers = e.getPassengers();
                            if (passengers.size() > 0) {
                                foundPassengers.addAll(passengers);
                                e.eject();
                            }
                        }
                    }
                } else {
                    if (jockeyedEntity != null) {
                        jockeyedEntity.eject();
                        jockeyedEntity = null;
                        snipeData.sendMessage(Messages.YOU_HAVE_BEEN_EJECTED);
                    }
                }
                return true;
            case ARROW:
                if (jockeyType == JockeyType.STACK) {
                    stack(snipeData);
                } else {
                    this.sitOn(snipeData);
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * Available types of jockey modes.
     */
    private enum JockeyType {
        NORMAL("Normal"),
        INVERSE("Inverse"),
        STACK("Stack");

        private final String name;

        JockeyType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }
}
