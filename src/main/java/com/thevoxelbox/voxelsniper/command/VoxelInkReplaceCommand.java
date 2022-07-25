package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class VoxelInkReplaceCommand extends VoxelCommand {

    public VoxelInkReplaceCommand() {
        super("VoxelInkReplace");
        setIdentifier("vir");
        setPermission("voxelsniper.sniper");
    }
    
    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        // Default command
        // Command: /vir info, /vir help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            sniper.sendMessage(Messages.VOXEL_INK_REPLACE_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            return true;
        }

        // Command: /vir
        if (args.length == 0) {
            Block selectedBlock = new BlockHelper(player, player.getWorld()).getTargetBlock();
            if (selectedBlock != null) {
                if (selectedBlock.getType() != snipeData.getReplaceMaterial()) {
                    sniper.sendMessage(Messages.VOXEL_INK_REPLACE_DIFFERENT_TYPE);
                } else {
                    snipeData.setReplaceSubstance(selectedBlock.getBlockData());
                    snipeData.getVoxelMessage().replaceData();
                }
            } else {
                sniper.sendMessage(Messages.VOXEL_INK_REPLACE_NO_BLOCK_TO_IMITATE_DATA);
            }
            return true;
        }

        // Command: /vir [data]
        if (args.length >= 1) {
            try {
                BlockData newData = snipeData.getReplaceMaterial().createBlockData("[" + String.join(",", args) + "]");
                BlockData activeData = snipeData.getReplaceSubstance();

                snipeData.setReplaceSubstance(activeData.merge(newData));
                snipeData.getVoxelMessage().replaceData();
            } catch (IllegalArgumentException e) {
                sniper.sendMessage(Messages.VOXEL_INK_REPLACE_CANT_IMITATE_DATA);
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

        String[] a = snipeData.getReplaceSubstance().getAsString().split("\\[");

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
