package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.*;
import com.thevoxelbox.voxelsniper.api.command.VoxelCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class VoxelReplaceCommand extends VoxelCommand {

    public VoxelReplaceCommand(final VoxelSniper plugin) {
        super("VoxelTarget", plugin);
        setIdentifier("vt");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        Sniper sniper = plugin.getSniperManager().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        if (args.length == 0) {
            Block selectedBlock = new RangeBlockHelper(player, player.getWorld()).getTargetBlock();
            if (selectedBlock != null) {
                snipeData.setTargetSubstance(selectedBlock.getBlockData());
                snipeData.getVoxelMessage().replace();
            }
            return true;
        }

        Material material = Material.matchMaterial(args[0]);
        if (material != null && material.isBlock()) {
            snipeData.setTargetSubstance(material.createBlockData());
            snipeData.getVoxelMessage().replace();
            return true;
        }

        player.sendMessage(ChatColor.RED + "You have entered an invalid item ID.");
        return true;
    }
}
