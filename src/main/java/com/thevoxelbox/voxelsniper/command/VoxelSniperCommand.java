package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.BukkitPlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class VoxelSniperCommand extends VoxelCommand {

    public VoxelSniperCommand() {

        super("VoxelSniper");
        setIdentifier("sniper");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(new BukkitPlayer(player));
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        // Default command
        // Command: /sniper, /sniper help, /sniper info
        if ((args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")))) {
            sniper.sendMessage(Messages.VOXELSNIPER_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            // TODO: List all bound tools
            // player.sendMessage(ChatColor.GOLD + "/" + "%alias%" + " list"); 
            // player.sendMessage(ChatColor.YELLOW + "    Lists all items that you have bound an action to.");
            return true;
        }

        if (args.length == 0) {
            sniper.sendMessage(Messages.CURRENT_BRUSH_SETTINGS);
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
                            sniper.sendMessage(Messages.NEGATIVE_VALUES_ARE_NOT_ALLOWED);
                        } else {
                            snipeData.setRange(range);
                            snipeData.setRanged(true);
                            snipeData.getVoxelMessage().toggleRange();
                        }
                    } catch (NumberFormatException exception) {
                        sniper.sendMessage(Messages.CAN_T_PARSE_NUMBER);
                    }
                }
                return true;
            } else if (args[0].equalsIgnoreCase("enable")) {
                sniper.setEnabled(true);
                sniper.sendMessage(Messages.VOXEL_SNIPER_IS_NOW_ENABLED_FOR_YOU);
                return true;
            } else if (args[0].equalsIgnoreCase("disable")) {
                sniper.setEnabled(false);
                sniper.sendMessage(Messages.VOXEL_SNIPER_IS_NOW_DISABLED_FOR_YOU);
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
