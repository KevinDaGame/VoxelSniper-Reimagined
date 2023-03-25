package com.github.kevindagame.snipe;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ervinnnc
 */
public class SnipeTool {

    private final BiMap<SnipeAction, VoxelMaterialType> actionTools = HashBiMap.create();
    private @NotNull IBrush currentBrush;
    private IBrush previousBrush = null;
    private final VoxelMessage messageHelper;
    private final SnipeData snipeData;

    protected SnipeTool(Sniper owner) {
        this(owner.instantiateBrush(VoxelBrushManager.getInstance().getDefaultBrush()), new SnipeData(owner));
    }

    protected SnipeTool(@Nullable IBrush brush, SnipeData snipeData) {
        this.snipeData = snipeData;
        messageHelper = new VoxelMessage(snipeData);
        snipeData.setVoxelMessage(messageHelper);
        this.currentBrush = brush;
    }

    public @NotNull IBrush getCurrentBrush() {
        return currentBrush;
    }

    public SnipeData getSnipeData() {
        return snipeData;
    }

    public boolean hasToolAssigned(VoxelMaterialType material) {
        return actionTools.containsValue(material);
    }

    public VoxelMessage getMessageHelper() {
        return messageHelper;
    }

    /**
     * Sets the current Brush to the previous Brush, and then returns the new Brush
     * @return The new Brush, or null if there is no previous Brush
     */
    public @Nullable IBrush previousBrush() {
        if (previousBrush == null) {
            return null;
        }
        return setCurrentBrush(previousBrush);
    }

    public void unassignAction(VoxelMaterialType itemInHand) {
        actionTools.inverse().remove(itemInHand);
    }

    public @NotNull IBrush setCurrentBrush(@NotNull IBrush brush) {
        previousBrush = currentBrush;
        currentBrush = brush;
        return brush;
    }

    public SnipeAction getActionAssigned(VoxelMaterialType itemInHand) {
        return actionTools.inverse().get(itemInHand);
    }

    public VoxelMaterialType getToolAssigned(SnipeAction action) {
        return actionTools.get(action);
    }

    public BiMap<SnipeAction, VoxelMaterialType> getActionTools() {
        return ImmutableBiMap.copyOf(actionTools);
    }


    public void assignAction(SnipeAction action, VoxelMaterialType itemInHand) {
        actionTools.forcePut(action, itemInHand);
    }

}
