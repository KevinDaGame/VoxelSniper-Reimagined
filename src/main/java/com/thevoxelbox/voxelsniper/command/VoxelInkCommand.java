package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VoxelInkCommand extends VoxelCommand {

    public VoxelInkCommand() {
        super("VoxelInk");
        setIdentifier("vi");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        // Default command
        // Command: /d info, /d help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            player.sendMessage(ChatColor.DARK_AQUA + getName() + " Command Syntax:");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + "");
            player.sendMessage(ChatColor.YELLOW + "    Copy data value of the block you are looking at into the active voxel material.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " [dataValue]");
            player.sendMessage(ChatColor.YELLOW + "    Set specified data value to the active voxel material.");
            player.sendMessage(ChatColor.DARK_AQUA + "    Example: /" + getActiveAlias() + " snowy=true,age=7");
            return true;
        }

        // Command: /vi          <- Sets the block user is looking at as voxel data values.
        if (args.length == 0) {
            Block selectedBlock = new BlockHelper(player, player.getWorld()).getTargetBlock();
            if (selectedBlock != null) {
                if (selectedBlock.getType() != snipeData.getVoxelMaterial()) {
                    player.sendMessage(ChatColor.RED + "That block is not the same as your active voxel material.");
                } else {
                    snipeData.setVoxelSubstance(selectedBlock.getBlockData());
                    snipeData.getVoxelMessage().data();
                }
            } else {
                player.sendMessage(ChatColor.GOLD + "No block to imitate voxel material data values. No changes were made.");
            }
            return true;
        }

        // Command: /vi [material]      <- Sets the defined material as voxel substance.
        if (args.length >= 1) {
            try {
                BlockData newData = snipeData.getVoxelMaterial().createBlockData("[" + String.join(",", args) + "]");
                BlockData activeData = snipeData.getVoxelSubstance();

                snipeData.setVoxelSubstance(activeData.merge(newData));
                snipeData.getVoxelMessage().data();
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "The data value(s) cannot be imitated to the active voxel material.");
            }

            return true;
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
        // TODO: Very hacky parsing, find a more elegant solution.
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        String[] a = snipeData.getVoxelSubstance().getAsString().split("\\[");

        if (a.length == 2) {
            List<String> possibleDataValues = new ArrayList<>();
            
            String values = a[1].replace("]", "");

            for (String value : values.split(",")) {
                possibleDataValues.add(value.split("=")[0]);
            }
            return possibleDataValues;
        }

        return new ArrayList<>();
    }
}
