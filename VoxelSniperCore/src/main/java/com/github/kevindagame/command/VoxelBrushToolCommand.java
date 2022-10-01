package com.github.kevindagame.command;

import com.google.common.collect.Lists;
import com.github.kevindagame.VoxelProfileManager;
import com.github.kevindagame.snipe.SnipeAction;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoxelBrushToolCommand extends VoxelCommand {

    // TODO: Config file saving of custom brush tools
    public VoxelBrushToolCommand() {
        super("Voxel Brush Tool");
        setIdentifier("btool");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);

        // Default command
        // Command: /btool, /btool help, /btool info
        if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")))) {
            sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            // TODO: List all bound tools
            // player.sendMessage(ChatColor.GOLD + "/" + "%alias%" + " list"); 
            // player.sendMessage(ChatColor.YELLOW + "    Lists all items that you have bound an action to.");
            return true;
        }

        // Command: /btool assign <arrow/powder> <label>
        if (args[0].equalsIgnoreCase("assign")) {
            SnipeAction action;
            if (args.length >= 3 && args[2] != null && !args[2].isEmpty()) {
                if (args[1].equalsIgnoreCase("arrow")) {
                    action = SnipeAction.ARROW;
                } else if (args[1].equalsIgnoreCase("powder")) {
                    action = SnipeAction.GUNPOWDER;
                } else {
                    return false;
                }

                VoxelMaterial itemInHand = (player.getItemInHand() != null) ? (player.getItemInHand()) : VoxelMaterial.AIR;

                if (itemInHand == null) {
                    sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_HOLD_ITEM);
                    return true;
                }

                if (itemInHand.isBlock()) {
                    sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_CANT_ASSIGN);
                    return true;
                }

                String toolLabel = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

                if (sniper.setTool(toolLabel, action, itemInHand)) {
                    sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_ASSIGNED.replace("%item%", itemInHand.getKey()).replace("%toolLabel%", toolLabel).replace("%action%", action.name()));
                } else {
                    sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_ASSIGN_FAIL);
                }

            } else {
                sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_ASSIGN_OWN);
            }
            return true;
        }


        // Command: /btool remove
        if (args[0].equalsIgnoreCase("remove")) {
            VoxelMaterial itemInHand = (player.getItemInHand() != null) ? player.getItemInHand() : VoxelMaterial.AIR;

            if (itemInHand == null) {
                sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_HOLD_UNASSIGN);
                return true;
            }

            if (sniper.getCurrentToolId() == null) {
                sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_NOT_ALLOWED_UNASSIGN);
                return true;
            }

            sniper.removeTool(sniper.getCurrentToolId(), itemInHand);
            sniper.sendMessage(Messages.VOXEL_BRUSH_TOOL_COMMAND_ASSIGNED_AS_TOOL.replace("%item%", itemInHand.getKey()));
            return true;
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        if (args.length == 1) {
            return Lists.newArrayList("assign", "remove");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("assign")) {
                return Lists.newArrayList("arrow", "powder");
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("assign")) {
                return Lists.newArrayList("[label]");
            }
        }

        return new ArrayList<>();
    }

}
