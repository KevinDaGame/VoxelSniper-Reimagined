package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import com.thevoxelbox.voxelsniper.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class VoxelVoxelCommand extends VoxelCommand {

    public VoxelVoxelCommand() {
        super("VoxelVoxel");
        setIdentifier("v");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public void registerTabCompletion(HashMap<Integer, List<String>> argumentListMap) {
        List<String> materials = new ArrayList<>();

        for (Material material : Material.values()) {
            if (material.isBlock()) {
                materials.add(material.getKey().toString());
            }
        }
        
        argumentListMap.put(1, materials);
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelSniper.getInstance().getSniperManager().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        // Command: /v          <- Sets the block user is looking at as voxel substance.
        if (args.length == 0) {
            Block selectedBlock = new BlockHelper(player, player.getWorld()).getTargetBlock();
            if (selectedBlock != null) {
                snipeData.setVoxelSubstance(selectedBlock.getBlockData());
                snipeData.getVoxelMessage().voxel();
            } else {
                player.sendMessage(ChatColor.GOLD + "Nothing to set voxel substance. No changes were made.");
            }
            return true;
        }

        // Command: /v [material]       <- Sets the defined material as voxel substance.
        Material material = Material.matchMaterial(args[0]); // TODO: Match old ID numbers to materials

        if (material != null && material.isBlock()) {
            snipeData.setVoxelSubstance(material.createBlockData());
            snipeData.getVoxelMessage().voxel();
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You have entered an invalid Item ID.");
            return true;
        }
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
        if (args.length == 1) {
            return getTabCompletion(1);
        }

        return new ArrayList<>();
    }
}
