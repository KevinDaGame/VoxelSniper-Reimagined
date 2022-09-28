package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.IPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;


public class VoxelVoxelCommand extends MaterialCommand {

    public VoxelVoxelCommand() {
        super("VoxelVoxel");
        setIdentifier("v");
        setPermission("voxelsniper.sniper");
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
}
