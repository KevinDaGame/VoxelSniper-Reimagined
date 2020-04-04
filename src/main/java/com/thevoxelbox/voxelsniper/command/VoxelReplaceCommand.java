package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.*;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import com.thevoxelbox.voxelsniper.util.MaterialTranslator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class VoxelReplaceCommand extends VoxelCommand {

    public VoxelReplaceCommand() {
        super("VoxelReplace");
        setIdentifier("vr");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public List<String> registerTabCompletion() {
        return Arrays.stream(Material.values()).filter(e -> e.isBlock()).map(e -> e.getKey().toString()).collect(Collectors.toList());
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());
        
        // Default command
        // Command: /vr info, /vr help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            player.sendMessage(ChatColor.DARK_AQUA + getName() + " Command Syntax:");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + "");
            player.sendMessage(ChatColor.YELLOW + "    Sets the block you are looking at as the active replace material.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " [material]");
            player.sendMessage(ChatColor.YELLOW + "    Sets the specified block as the active replace material.");
            return true;
        }

        // Command: /vr          <- Sets the block user is looking at as voxel substance.
        if (args.length == 0) {
            Block selectedBlock = new BlockHelper(player, player.getWorld()).getTargetBlock();
            if (selectedBlock != null) {
                snipeData.setVoxelSubstance(selectedBlock.getBlockData());
                snipeData.getVoxelMessage().voxel();
            } else {
                player.sendMessage(ChatColor.GOLD + "Nothing to imitate replace material. No changes were made.");
            }
            return true;
        }

        // Command: /vr [material]       <- Sets the defined material as voxel substance.
        Material material = Material.matchMaterial(args[0]); // TODO: Match old ID numbers to materials
        
        if (material == null) {
            material = MaterialTranslator.resolveMaterial(args[0]);
        }

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
            // Preprocess the string for partial matching
            args[0] = args[0].toLowerCase();

            if (!args[0].startsWith("minecraft:")) {
                if (args[0].startsWith("mi") && !args[0].equals("minecraft:")) {
                    return Lists.newArrayList("minecraft:");
                }

                args[0] = "minecraft:" + args[0];
            }

            return getTabCompletion(1);
        }

        return new ArrayList<>();
    }
}
