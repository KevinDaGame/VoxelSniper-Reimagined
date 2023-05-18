package com.github.kevindagame.snipe;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

/**
 * @author ervinnnc
 */
public class SnipeTool {

    private final Map<VoxelMaterial, SnipeAction> actionTools = new HashMap<>();
    private IBrush currentBrush;
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

    public IBrush getCurrentBrush() {
        return currentBrush;
    }

    public SnipeData getSnipeData() {
        return snipeData;
    }

    public boolean hasToolAssigned(VoxelMaterial material) {
        return actionTools.containsKey(material);
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

    public void unassignAction(VoxelMaterial itemInHand) {
        actionTools.remove(itemInHand);
    }

    public IBrush setCurrentBrush(IBrush brush) {
        previousBrush = currentBrush;
        currentBrush = brush;
        return brush;
    }

    public SnipeAction getActionAssigned(VoxelMaterial itemInHand) {
        return actionTools.get(itemInHand);
    }

    public Map<VoxelMaterial, SnipeAction> getActionTools() {
        return Collections.unmodifiableMap(this.actionTools);
    }


    public void assignAction(SnipeAction action, VoxelMaterial itemInHand) {
        actionTools.put(itemInHand, action);
    }

}
