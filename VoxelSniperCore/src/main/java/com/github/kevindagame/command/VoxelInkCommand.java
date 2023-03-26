package com.github.kevindagame.command;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelmaterial.BasicVoxelMaterial;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;

import java.util.ArrayList;
import java.util.List;

public class VoxelInkCommand extends VoxelCommand {

    public VoxelInkCommand() {
        super("VoxelInk");
        setIdentifier("vi");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = player.getSniper();
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
                    snipeData.setVoxelSubstance(new BasicVoxelMaterial(selectedBlock.getBlockData()));
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
            IBlockData activeData = snipeData.getVoxelSubstance().getMaterial();

            snipeData.setVoxelSubstance(new BasicVoxelMaterial(newData));
            snipeData.getVoxelMessage().data();
        } catch (IllegalArgumentException e) {
            sniper.sendMessage(Messages.VOXEL_INK_CANT_IMITATE_DATA);
        }

        return true;
    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        // TODO: Very hacky parsing, find a more elegant solution.
        Sniper sniper = player.getSniper();
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        String[] a = snipeData.getVoxelSubstance().getMaterial().getAsString().split("\\[");

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
