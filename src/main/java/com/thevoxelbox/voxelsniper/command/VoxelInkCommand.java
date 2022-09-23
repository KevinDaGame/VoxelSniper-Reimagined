package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.bukkit.BukkitVoxelSniper;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.BukkitPlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class VoxelInkCommand extends VoxelCommand {

    public VoxelInkCommand() {
        super("VoxelInk");
        setIdentifier("vi");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player bukkitPlayer, String[] args) {
        BukkitPlayer player = BukkitVoxelSniper.getInstance().getPlayer(bukkitPlayer);
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        // Default command
        // Command: /d info, /d help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            sniper.sendMessage(Messages.VOXEL_INK_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            return true;
        }

        // Command: /vi          <- Sets the block user is looking at as voxel data values.
        if (args.length == 0) {
            IBlock selectedBlock = new BlockHelper(player).getTargetBlock();
            if (selectedBlock != null) {
                if (selectedBlock.getMaterial() != snipeData.getVoxelMaterial()) {
                    sniper.sendMessage(Messages.VOXEL_INK_DIFFERENT_TYPE);
                } else {
                    snipeData.setVoxelSubstance(selectedBlock.getBlockData());
                    snipeData.getVoxelMessage().data();
                }
            } else {
                sniper.sendMessage(Messages.VOXEL_INK_NO_BLOCK_TO_IMITATE_DATA);
            }
            return true;
        }

        // Command: /vi [material]      <- Sets the defined material as voxel substance.
        try {
            IBlockData newData = snipeData.getVoxelMaterial().createBlockData("[" + String.join(",", args) + "]");
            IBlockData activeData = snipeData.getVoxelSubstance();

            snipeData.setVoxelSubstance(activeData.merge(newData));
            snipeData.getVoxelMessage().data();
        } catch (IllegalArgumentException e) {
            sniper.sendMessage(Messages.VOXEL_INK_CANT_IMITATE_DATA);
        }

        return true;
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
        // TODO: Very hacky parsing, find a more elegant solution.
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(BukkitVoxelSniper.getInstance().getPlayer(player));
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
