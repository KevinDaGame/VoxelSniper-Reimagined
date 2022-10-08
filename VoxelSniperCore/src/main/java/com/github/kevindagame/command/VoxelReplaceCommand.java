package com.github.kevindagame.command;

import com.github.kevindagame.VoxelProfileManager;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

public class VoxelReplaceCommand extends MaterialCommand {

    public VoxelReplaceCommand() {
        super("VoxelReplace");
        setIdentifier("vr");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {

        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        // Default command
        // Command: /vr info, /vr help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            sniper.sendMessage(Messages.VOXEL_REPLACE_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            return true;
        }

        // Command: /vr          <- Sets the block user is looking at as voxel substance.
        if (args.length == 0) {
            IBlock selectedBlock = new BlockHelper(sniper.getPlayer()).getTargetBlock();
            if (selectedBlock != null) {
                snipeData.setReplaceSubstance(selectedBlock.getBlockData());
                snipeData.getVoxelMessage().replace();
            } else {
                sniper.sendMessage(Messages.REPLACE_NOTHING_TO_IMITATE);
            }
            return true;
        }

        // Command: /vr [material]       <- Sets the defined material as voxel substance.
        VoxelMaterial material = VoxelMaterial.getMaterial(args[0]); // TODO: Match old ID numbers to materials
        if (material != null && material.isBlock()) {
            snipeData.setReplaceSubstance(material.createBlockData());
            snipeData.getVoxelMessage().replace();
        } else {
            sniper.sendMessage(Messages.INVALID_ITEM_ID);
        }
        return true;
    }
}
