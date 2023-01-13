package com.github.kevindagame.command;

import com.github.kevindagame.VoxelProfileManager;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class VoxelSniperCommand extends VoxelCommand {

    public VoxelSniperCommand() {

        super("VoxelSniper");
        setIdentifier("sniper");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
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

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        if (args.length == 1) {
            return Lists.newArrayList("enable", "disable", "range");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("range")) {
                return Lists.newArrayList("[number]");
            }
        }

        return new ArrayList<>();
    }
}
