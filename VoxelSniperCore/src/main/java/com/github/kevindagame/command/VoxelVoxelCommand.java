package com.github.kevindagame.command;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.player.materialChange.PlayerMaterialChangedEvent;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;


public class VoxelVoxelCommand extends MaterialCommand {

    public VoxelVoxelCommand() {
        super("VoxelVoxel");
        setIdentifier("v");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = player.getSniper();
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
                if (!new PlayerMaterialChangedEvent(sniper.getPlayer(), sniper.getSnipeData(sniper.getCurrentToolId()).getVoxelSubstance(), selectedBlock.getBlockData()).callEvent().isCancelled()) {
                    snipeData.setVoxelSubstance(selectedBlock.getBlockData());
                    snipeData.getVoxelMessage().voxel();
                }
            } else {
                sniper.sendMessage(Messages.NOTHING_TO_SET_SUBSTANCE);
            }
            return true;
        }

        // Command: /v [material]       <- Sets the defined material as voxel substance.
        VoxelMaterialType material = VoxelMaterialType.getMaterial(args[0]);

        if (material != null && material.isBlock()) {
            if (!new PlayerMaterialChangedEvent(sniper.getPlayer(), sniper.getSnipeData(sniper.getCurrentToolId()).getVoxelSubstance(), material.createBlockData()).callEvent().isCancelled()) {
                snipeData.setVoxelSubstance(material.createBlockData());
                snipeData.getVoxelMessage().voxel();
            }
        } else {
            sniper.sendMessage(Messages.INVALID_TYPE_ID);
        }
        return true;
    }
}
