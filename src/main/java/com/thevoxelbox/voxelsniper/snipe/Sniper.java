package com.thevoxelbox.voxelsniper.snipe;

import com.google.common.collect.Maps;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.brush.IBrush;
import com.thevoxelbox.voxelsniper.brush.perform.IPerformerBrush;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.event.SniperMaterialChangedEvent;
import com.thevoxelbox.voxelsniper.event.SniperReplaceMaterialChangedEvent;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class Sniper implements Audience {

    private final VoxelSniper plugin;
    private final UUID player;
    private boolean enabled = true;
    private final LinkedList<Undo> undoList = new LinkedList<>();
    private final Map<String, SnipeTool> tools = Maps.newHashMap();

    public Sniper(VoxelSniper plugin, Player player) {
        this.plugin = plugin;
        this.player = player.getUniqueId();
        SnipeTool sniperTool = new SnipeTool(this);
        sniperTool.assignAction(SnipeAction.ARROW, Material.ARROW);
        sniperTool.assignAction(SnipeAction.GUNPOWDER, Material.GUNPOWDER);
        tools.put(null, sniperTool);
    }

    public String getCurrentToolId() {
        //TODO this isn't quite right
        return getToolId((getPlayer().getItemInHand() != null) ? getPlayer().getItemInHand().getType() : null);
    }

    public String getToolId(Material itemInHand) {
        if (itemInHand == null) {
            return null;
        }

        for (Map.Entry<String, SnipeTool> entry : tools.entrySet()) {
            if (entry.getValue().hasToolAssigned(itemInHand)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    /**
     * Sniper execution call.
     *
     * @param action       Action player performed
     * @param itemInHand   Item in hand of player
     * @param clickedBlock Block that the player targeted/interacted with
     * @param clickedFace  Face of that targeted Block
     * @return true if command visibly processed, false otherwise.
     */
    public boolean snipe(Action action, Material itemInHand, Block clickedBlock, BlockFace clickedFace) {
        String toolId = getToolId(itemInHand);
        SnipeTool sniperTool = tools.get(toolId);

        //Confirm that action is a left or right click
        switch (action) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                break;
            default:
                return false;
        }

        if (!sniperTool.hasToolAssigned(itemInHand)) {
            return false;
        }
        if (sniperTool.getCurrentBrush() == null) {
            sendMessage(Messages.NO_BRUSH_SELECTED);
            return true;
        }

        String permissionNode = sniperTool.getCurrentBrush().getPermissionNode();
        if (!getPlayer().hasPermission(permissionNode)) {
            sendMessage(Messages.NO_PERMISSION_BRUSH.replace("%permissionNode%",permissionNode));
            return true;
        }

        SnipeData snipeData = sniperTool.getSnipeData();
        SnipeAction snipeAction = sniperTool.getActionAssigned(itemInHand);
        Block targetBlock;
        Block lastBlock = null;

        if (clickedBlock != null) {
            targetBlock = clickedBlock;
        } else {
            BlockHelper rangeBlockHelper = snipeData.isRanged() ? new BlockHelper(getPlayer(), getPlayer().getWorld(), snipeData.getRange()) : new BlockHelper(getPlayer(), getPlayer().getWorld());
            targetBlock = snipeData.isRanged() ? rangeBlockHelper.getRangeBlock() : rangeBlockHelper.getTargetBlock();
            lastBlock = rangeBlockHelper.getLastBlock();
        }

        if (getPlayer().isSneaking()) {
            switch (action) {
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    BlockData oldSubstance, newSubstance;
                    switch (snipeAction) {
                        case GUNPOWDER:
                            oldSubstance = snipeData.getReplaceSubstance();
                            snipeData.setReplaceSubstance(targetBlock != null ? targetBlock.getBlockData() : SnipeData.DEFAULT_VOXEL_SUBSTANCE);
                            newSubstance = snipeData.getReplaceSubstance();
                            Bukkit.getPluginManager().callEvent(new SniperReplaceMaterialChangedEvent(this, toolId, oldSubstance, newSubstance));

                            snipeData.getVoxelMessage().replace();
                            return true;
                        case ARROW:
                            oldSubstance = snipeData.getVoxelSubstance();
                            snipeData.setVoxelSubstance(targetBlock != null ? targetBlock.getBlockData() : SnipeData.DEFAULT_VOXEL_SUBSTANCE);
                            newSubstance = snipeData.getVoxelSubstance();
                            Bukkit.getPluginManager().callEvent(new SniperMaterialChangedEvent(this, toolId, oldSubstance, newSubstance));
                            snipeData.getVoxelMessage().voxel();
                            return true;
                        default:
                            return false;
                    }
                default:
                    break;
            }
        }
        switch(action) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                if (clickedBlock == null) {
                    if (targetBlock == null || lastBlock == null) {
                        sendMessage(Messages.TARGET_MUST_BE_VISIBLE);
                        return true;
                    }
                }

                if (sniperTool.getCurrentBrush() instanceof PerformerBrush) {
                    PerformerBrush performerBrush = (PerformerBrush) sniperTool.getCurrentBrush();
                    performerBrush.initP(snipeData);
                }

                return sniperTool.getCurrentBrush().perform(snipeAction, snipeData, targetBlock, lastBlock);
        }
        return false;
    }


    public IBrush setBrush(String toolId, Class<? extends IBrush> brush) {
        if (!tools.containsKey(toolId)) {
            return null;
        }

        return tools.get(toolId).setCurrentBrush(brush);
    }

    public IBrush getBrush(String toolId) {
        if (!tools.containsKey(toolId)) {
            return null;
        }

        return tools.get(toolId).getCurrentBrush();
    }

    public IBrush previousBrush(String toolId) {
        if (!tools.containsKey(toolId)) {
            return null;
        }

        return tools.get(toolId).previousBrush();
    }

    public boolean setTool(String toolId, SnipeAction action, Material itemInHand) {
        for (Map.Entry<String, SnipeTool> entry : tools.entrySet()) {
            if (entry.getKey() != toolId && entry.getValue().hasToolAssigned(itemInHand)) {
                return false;
            }
        }

        if (!tools.containsKey(toolId)) {
            SnipeTool tool = new SnipeTool(this);
            tools.put(toolId, tool);
        }
        tools.get(toolId).assignAction(action, itemInHand);
        return true;
    }

    public void removeTool(String toolId, Material itemInHand) {
        if (!tools.containsKey(toolId)) {
            SnipeTool tool = new SnipeTool(this);
            tools.put(toolId, tool);
        }
        tools.get(toolId).unassignAction(itemInHand);
    }

    public void removeTool(String toolId) {
        if (toolId == null) {
            return;
        }
        tools.remove(toolId);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void storeUndo(Undo undo) {
        if (VoxelSniper.getInstance().getVoxelSniperConfiguration().getUndoCacheSize() <= 0) {
            return;
        }
        if (undo != null && undo.getSize() > 0) {
            while (undoList.size() >= plugin.getVoxelSniperConfiguration().getUndoCacheSize()) {
                this.undoList.pollLast();
            }
            undoList.push(undo);
        }
    }

    public int undo() {
        return undo(1);
    }

    public int undo(int amount) {
        int changedBlocks = 0;
        if (this.undoList.isEmpty()) {
            sendMessage(Messages.NOTHING_TO_UNDO);
        } else {
            for (int x = 0; x < amount && !undoList.isEmpty(); x++) {
                Undo undo = this.undoList.pop();
                if (undo != null) {
                    undo.undo();
                    changedBlocks += undo.getSize();
                } else { // TODO: Check if this logic makes sense
                    break;
                }
            }

            sendMessage(Messages.UNDO_SUCCESSFUL.replace("%changedBlocks%", String.valueOf(changedBlocks)));;
        }
        return changedBlocks;
    }

    public void reset(String toolId) {
        SnipeTool backup = tools.remove(toolId);
        SnipeTool newTool = new SnipeTool(this);

        for (Map.Entry<SnipeAction, Material> entry : backup.getActionTools().entrySet()) {
            newTool.assignAction(entry.getKey(), entry.getValue());
        }
        tools.put(toolId, newTool);
    }

    public SnipeData getSnipeData(String toolId) {
        return tools.containsKey(toolId) ? tools.get(toolId).getSnipeData() : null;
    }

    public void displayInfo() {
        String currentToolId = getCurrentToolId();
        SnipeTool sniperTool = tools.get(currentToolId);
        IBrush brush = sniperTool.getCurrentBrush();
        sendMessage(Messages.CURRENT_TOOL.replace("%tool%", (currentToolId != null) ? currentToolId : "Default Tool"));
        if (brush == null) {
            sendMessage(Messages.NO_BRUSH_SELECTED);
            return;
        }
        brush.info(sniperTool.getMessageHelper());
        if (brush instanceof IPerformerBrush) {
            ((IPerformerBrush) brush).showInfo(sniperTool.getMessageHelper());
        }
    }

    public SnipeTool getSnipeTool(String toolId) {
        return tools.get(toolId);
    }

    @Override
    public void sendMessage(final @NotNull Identity source, final @NotNull Component message, final @NotNull MessageType type) {
        VoxelSniper.getAdventure().player(this.getPlayer()).sendMessage(source, message, type);
    }
}
