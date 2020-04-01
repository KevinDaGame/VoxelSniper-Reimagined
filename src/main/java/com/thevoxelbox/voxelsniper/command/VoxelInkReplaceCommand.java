package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.RangeBlockHelper;
import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.Sniper;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.api.command.VoxelCommand;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class VoxelInkReplaceCommand extends VoxelCommand {

    public VoxelInkReplaceCommand(final VoxelSniper plugin) {
        super("VoxelInkReplace", plugin);
        setIdentifier("vir");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        Sniper sniper = plugin.getSniperManager().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());
        BlockData blockData;

        if (args.length == 0) {
            Block targetBlock = new RangeBlockHelper(player, player.getWorld()).getTargetBlock();
            if (targetBlock != null) {
                blockData = targetBlock.getBlockData();
            } else {
                return true;
            }
        } else {
            try {
                blockData = snipeData.getTargetMaterial().createBlockData("[" + args[0] + "]");
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "Invalid block data for current material!");
                return true;
            }
        }

        snipeData.setTargetSubstance(blockData);
        snipeData.getVoxelMessage().replaceData();
        return true;
    }
}
