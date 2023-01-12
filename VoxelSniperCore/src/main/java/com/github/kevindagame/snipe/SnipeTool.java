package com.github.kevindagame.snipe;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.brush.SnipeBrush;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import org.jetbrains.annotations.NotNull;

/**
 * @author ervinnnc
 */
public class SnipeTool {

    private final BiMap<SnipeAction, VoxelMaterial> actionTools = HashBiMap.create();
    IBrush currentBrush;
    VoxelMessage messageHelper;

    SnipeData snipeData;
    private IBrush previousBrush;

    protected SnipeTool(Sniper owner) {
        this(owner.instantiateBrush(SnipeBrush.class), new SnipeData(owner));
    }

    protected SnipeTool(IBrush brush, SnipeData snipeData) {
        this.snipeData = snipeData;
        messageHelper = new VoxelMessage(snipeData);
        snipeData.setVoxelMessage(messageHelper);
        if (snipeData.owner().getPlayer().hasPermission(brush.getPermissionNode()) || snipeData.owner().getPlayer().hasPermission("voxelsniper.brush.*")) {
            this.currentBrush = brush;
        }
    }

    public IBrush getCurrentBrush() {
        if (currentBrush == null) {
            return null;
        }
        return currentBrush;
    }

    public SnipeData getSnipeData() {
        return snipeData;
    }

    public boolean hasToolAssigned(VoxelMaterial material) {
        return actionTools.containsValue(material);
    }

    public VoxelMessage getMessageHelper() {
        return messageHelper;
    }

    public IBrush previousBrush() {
        if (previousBrush == null) {
            return null;
        }
        return setCurrentBrush(previousBrush);
    }

    public void unassignAction(VoxelMaterial itemInHand) {
        actionTools.inverse().remove(itemInHand);
    }

    public IBrush setCurrentBrush(@NotNull IBrush brush) {
        Preconditions.checkNotNull(brush, "Can't set brush to null.");
        if (snipeData.owner().getPlayer().hasPermission(brush.getPermissionNode()) || snipeData.owner().getPlayer().hasPermission("voxelsniper.brush.*")) {
            previousBrush = currentBrush;
            currentBrush = brush;
            return brush;
        }
        if (snipeData.owner().getPlayer().hasPermission(brush.getPermissionNode()) || snipeData.owner().getPlayer().hasPermission("voxelsniper.brush.*")) {
            previousBrush = currentBrush;
            currentBrush = brush;
            return brush;
        }
        return null;
    }

    public SnipeAction getActionAssigned(VoxelMaterial itemInHand) {
        return actionTools.inverse().get(itemInHand);
    }

    public VoxelMaterial getToolAssigned(SnipeAction action) {
        return actionTools.get(action);
    }

    public BiMap<SnipeAction, VoxelMaterial> getActionTools() {
        return ImmutableBiMap.copyOf(actionTools);
    }


    public void assignAction(SnipeAction action, VoxelMaterial itemInHand) {
        actionTools.forcePut(action, itemInHand);
    }

}
