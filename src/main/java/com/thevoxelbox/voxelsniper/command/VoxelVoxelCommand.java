package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.IPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.List;

public class VoxelVoxelCommand extends VoxelCommand {

    public VoxelVoxelCommand() {
        super("VoxelVoxel");
        setIdentifier("v");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public List<String> registerTabCompletion() {
        return VoxelMaterial.getMaterials().stream().filter(VoxelMaterial::isBlock).map(VoxelMaterial::getKey).toList();
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());
        
        // Default command
        // Command: /vr info, /vr help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            sniper.sendMessage(Messages.VOXEL_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            return true;
        }

        // Command: /v          <- Sets the block user is looking at as voxel substance.
        if (args.length == 0) {
            IBlock selectedBlock = new BlockHelper(sniper.getPlayer()).getTargetBlock();
            if (selectedBlock != null) {
                snipeData.setVoxelSubstance(selectedBlock.getBlockData());
                snipeData.getVoxelMessage().voxel();
            } else {
                sniper.sendMessage(Messages.NOTHING_TO_SET_SUBSTANCE);
            }
            return true;
        }

        // Command: /v [material]       <- Sets the defined material as voxel substance.
        VoxelMaterial material = VoxelMaterial.getMaterial(args[0]);

        if (material != null && material.isBlock()) {
            snipeData.setVoxelSubstance(material.createBlockData());
            snipeData.getVoxelMessage().voxel();
        } else {
            sniper.sendMessage(Messages.INVALID_TYPE_ID);
        }
        return true;
    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        if (args.length == 1) {
            // Preprocess the string for partial matching
            args[0] = args[0].toLowerCase();

            if (!args[0].startsWith("minecraft:")) {
                if (args[0].startsWith("mi")) {
                    return Lists.newArrayList("minecraft:");
                }

                args[0] = "minecraft:" + args[0];
            }

            return getTabCompletion(1);
        }

        return new ArrayList<>();
    }
}
