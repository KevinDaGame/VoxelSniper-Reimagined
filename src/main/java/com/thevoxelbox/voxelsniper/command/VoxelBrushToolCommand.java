package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeAction;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class VoxelBrushToolCommand extends VoxelCommand {

    // TODO: Config file saving of custom brush tools
    public VoxelBrushToolCommand() {
        super("Voxel Brush Tool");
        setIdentifier("btool");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);

        // Default command
        // Command: /btool, /btool help, /btool info
        if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")))) {
            player.sendMessage(ChatColor.DARK_AQUA + getName() + " Command Syntax:");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " assign <arrow | powder> <label>");
            player.sendMessage(ChatColor.YELLOW + "    Assign an action to your currently held item with the specified label.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " remove");
            player.sendMessage(ChatColor.YELLOW + "    Removes the action that is bound to the currently held item.");
            // TODO: List all bound tools
            // player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " list"); 
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

                Material itemInHand = (player.getInventory().getItemInMainHand() != null) ? player.getInventory().getItemInMainHand().getType() : null;

                if (itemInHand == null) {
                    player.sendMessage(ChatColor.RED + "Please hold an item to assign a tool action to.");
                    return true;
                }
                
                if (itemInHand.isBlock()) {
                    player.sendMessage(ChatColor.RED + "You can't assign an action to an item that can be placed as a block!");
                    return true;
                }

                String toolLabel = Arrays.stream(Arrays.copyOfRange(args, 2, args.length)).collect(Collectors.joining(" "));

                if (sniper.setTool(toolLabel, action, itemInHand)) {
                    player.sendMessage(ChatColor.GOLD + itemInHand.name() + " has been assigned to '" + toolLabel + "' as action " + action.name() + ".");
                } else {
                    player.sendMessage(ChatColor.RED + "Couldn't assign action to that tool.");
                }

                return true;
            } else {
                player.sendMessage(ChatColor.DARK_AQUA + "Please assign your own label to the tool to identify it.");
                return true;
            }
        }

        
        // Command: /btool remove
        if (args[0].equalsIgnoreCase("remove")) {
            Material itemInHand = (player.getInventory().getItemInMainHand() != null) ? player.getInventory().getItemInMainHand().getType() : null;

            if (itemInHand == null) {
                player.sendMessage(ChatColor.RED + "Please hold an item to unassign a tool action.");
                return true;
            }

            if (sniper.getCurrentToolId() == null) {
                player.sendMessage(ChatColor.RED + "You are not allowed to unassign the default tool!");
                return true;
            }

            sniper.removeTool(sniper.getCurrentToolId(), itemInHand);
            player.sendMessage(ChatColor.GOLD + itemInHand.name() + " has been unassigned as a tool.");
            return true;
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
        if (args.length == 1) {
            return Lists.newArrayList("assign", "remove");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("assign")) {
                return Lists.newArrayList("arrow", "powder");
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("assign"))  {
                return Lists.newArrayList("[label]");
            }
        }

        return new ArrayList<>();
    }

}
