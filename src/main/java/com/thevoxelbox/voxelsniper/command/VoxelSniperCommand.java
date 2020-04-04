package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class VoxelSniperCommand extends VoxelCommand {

    public VoxelSniperCommand() {

        super("VoxelSniper");
        setIdentifier("sniper");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        // Default command
        // Command: /sniper, /sniper help, /sniper info
        if ((args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")))) {
            player.sendMessage(ChatColor.DARK_AQUA + getName() + " Command Syntax:");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " <enable | disable>");
            player.sendMessage(ChatColor.YELLOW + "    Activates or deactivates VoxelSniper for yourself.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " range");
            player.sendMessage(ChatColor.YELLOW + "    Toggles whether range limit is enabled or not.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " range [number]");
            player.sendMessage(ChatColor.YELLOW + "    Sets and enables the range limitation.");
            // TODO: List all bound tools
            // player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " list"); 
            // player.sendMessage(ChatColor.YELLOW + "    Lists all items that you have bound an action to.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.DARK_RED + "VoxelSniper - Current Brush Settings:");
            sniper.displayInfo();
            return true;
        }

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("range")) {
                // Command: /sniper range
                if (args.length == 1) {
                    snipeData.setRanged(!snipeData.isRanged());
                    snipeData.getVoxelMessage().toggleRange();
                }

                // Command: /sniper range [number]
                if (args.length == 2) {
                    try {
                        int range = Integer.parseInt(args[1]);
                        if (range < 0) {
                            player.sendMessage("Negative values are not allowed.");
                        } else {
                            snipeData.setRange(range);
                            snipeData.setRanged(true);
                            snipeData.getVoxelMessage().toggleRange();
                        }
                    } catch (NumberFormatException exception) {
                        player.sendMessage("Can't parse number.");
                    }
                }
                return true;
            } else if (args[0].equalsIgnoreCase("enable")) {
                sniper.setEnabled(true);
                player.sendMessage("VoxelSniper is now enabled for you.");
                return true;
            } else if (args[0].equalsIgnoreCase("disable")) {
                sniper.setEnabled(false);
                player.sendMessage("VoxelSniper is now disabled for you.");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
        if (args.length == 0) {
            return Lists.newArrayList("enable", "disable", "range");
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("range")) {
                return Lists.newArrayList("[number]");
            }
        }

        return new ArrayList<>();
    }
}
