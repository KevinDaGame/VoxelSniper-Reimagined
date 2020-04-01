package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.RangeBlockHelper;
import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.Sniper;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.api.command.VoxelCommand;
import javafx.scene.paint.Color;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class VoxelInkCommand extends VoxelCommand {

    public VoxelInkCommand(final VoxelSniper plugin) {
        super("VoxelInk", plugin);
        setIdentifier("vi");
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
                blockData = snipeData.getVoxelMaterial().createBlockData("[" + args[0] + "]");
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "Invalid block data for current Material!");
                return true;
            }
        }

        snipeData.setVoxelSubstance(blockData);
        snipeData.getVoxelMessage().data();
        return true;
    }
}
