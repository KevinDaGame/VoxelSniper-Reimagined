package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.*;
import com.thevoxelbox.voxelsniper.api.command.VoxelCommand;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class VoxelListCommand extends VoxelCommand {

    public VoxelListCommand(final VoxelSniper plugin) {
        super("VoxelList", plugin);
        setIdentifier("vl");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        Sniper sniper = plugin.getSniperManager().getSniperForPlayer(player);

        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());
        if (args.length == 0) {
            final RangeBlockHelper rangeBlockHelper = new RangeBlockHelper(player, player.getWorld());
            final Block targetBlock = rangeBlockHelper.getTargetBlock();
            snipeData.getVoxelList().add(targetBlock.getBlockData().getMaterial());
            snipeData.getVoxelMessage().voxelList();
            return true;
        } else {
            if (args[0].equalsIgnoreCase("clear")) {
                snipeData.getVoxelList().clear();
                snipeData.getVoxelMessage().voxelList();
                return true;
            }
        }

        boolean remove = false;

        for (final String string : args) {
            String materialId;

            if (string.startsWith("-")) {
                remove = true;
                materialId = string.replaceAll("-", "");
            } else {
                materialId = string;
            }

            Material material = Material.matchMaterial(materialId);

            if (material != null && material.isBlock()) {
                if (!remove) {
                    snipeData.getVoxelList().add(material);
                    snipeData.getVoxelMessage().voxelList();
                } else {
                    snipeData.getVoxelList().remove(material);
                    snipeData.getVoxelMessage().voxelList();
                }
            }

        }
        return true;
    }
}
