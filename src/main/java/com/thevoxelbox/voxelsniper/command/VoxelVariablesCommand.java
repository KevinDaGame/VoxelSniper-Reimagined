package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ervinnnc
 */
public class VoxelVariablesCommand extends VoxelCommand {

    public VoxelVariablesCommand() {
        super("Voxel Variables");
        setIdentifier("vv");
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

        if (getActiveAlias().equalsIgnoreCase("vc")) {
            try {
                snipeData.setcCen(Integer.parseInt(args[0]));
                snipeData.getVoxelMessage().center();
                return true;
            } catch (NumberFormatException exception) {
                player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " <number>");
                player.sendMessage(ChatColor.YELLOW + "    Changes the voxel center parameter of the brush to the specified number.");
                return true;
            }
        }

        if (getActiveAlias().equalsIgnoreCase("vh")) {
            try {
                snipeData.setVoxelHeight(Integer.parseInt(args[0]));
                snipeData.getVoxelMessage().height();
                return true;
            } catch (NumberFormatException exception) {
                player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " height <number>");
                player.sendMessage(ChatColor.YELLOW + "    Changes the voxel height parameter of the brush to the specified number.");
                return false;
            }
        }

        if (getActiveAlias().equalsIgnoreCase("vl")) {
            if (args.length == 0 && args[0].equalsIgnoreCase("clear")) {
                snipeData.getVoxelList().clear();
                snipeData.getVoxelMessage().voxelList();
                return true;
            }
            
            if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("help")))) {
                player.sendMessage(ChatColor.DARK_AQUA + "Using Voxel List:");
                player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " clear");
                player.sendMessage(ChatColor.YELLOW + "    Clears the contents of the VoxelList.");
                player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " <material>[-]...");
                player.sendMessage(ChatColor.YELLOW + "    Adds or removes the specified material into the voxel list.");
                player.sendMessage(ChatColor.DARK_AQUA + "    Example: /" + getActiveAlias() + " list dirt stone- wheat");
                player.sendMessage(ChatColor.AQUA + "      Adds dirt, removes stone, adds wheat; in order");
                player.sendMessage(ChatColor.DARK_AQUA + "    Example: /" + getActiveAlias() + " list dirt- grass_block");
                player.sendMessage(ChatColor.AQUA + "      Removes dirt, adds grass_block; in order");
                return true;
            }

            List<String> invalidMaterials = new ArrayList<>();
            for (final String string : args) {
                boolean remove = string.contains("-");
                IMaterial material = Material.matchMaterial(string.toLowerCase().replace("-", ""));

                if (material == null || !material.isBlock()) {
                    invalidMaterials.add(string.replace("-", ""));
                    continue;
                }

                if (!remove) {
                    snipeData.getVoxelList().add(material);
                } else {
                    snipeData.getVoxelList().remove(material);
                }
            }

            snipeData.getVoxelMessage().voxelList();

            if (!invalidMaterials.isEmpty()) {
                player.sendMessage(ChatColor.RED + "Couldn't add because item is non-existent or aren't blocks:- ");
                player.sendMessage(ChatColor.GOLD + "    " + String.join(", ", invalidMaterials));
            }
            return true;
        }

        // Default command
        // Command: /vir info, /vir help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            player.sendMessage(ChatColor.DARK_AQUA + getName() + " Command Syntax:");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " height <number>");
            player.sendMessage(ChatColor.YELLOW + "    Changes the voxel height parameter of the brush to the specified number.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " center <number>");
            player.sendMessage(ChatColor.YELLOW + "    Changes the voxel center parameter of the brush to the specified number.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " list");
            player.sendMessage(ChatColor.YELLOW + "    Shows you how to use voxel list.");
            return true;
        }

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("center")) {
                try {
                    snipeData.setcCen(Integer.parseInt(args[0]));
                    snipeData.getVoxelMessage().center();
                    return true;
                } catch (NumberFormatException exception) {
                    return false;
                }
            }

            if (args[0].equalsIgnoreCase("height")) {
                try {
                    snipeData.setVoxelHeight(Integer.parseInt(args[0]));
                    snipeData.getVoxelMessage().height();
                    return true;
                } catch (NumberFormatException exception) {
                    return false;
                }
            }

            if (args[0].equalsIgnoreCase("list")) {
                if (args.length == 1) {
                    player.sendMessage(ChatColor.DARK_AQUA + "Using Voxel List:");
                    player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " list <material>[-]...");
                    player.sendMessage(ChatColor.YELLOW + "    Adds or removes the specified material into the voxel list.");
                    player.sendMessage(ChatColor.DARK_AQUA + "    Example: /" + getActiveAlias() + " list acacia_log stone- grass_block");
                    player.sendMessage(ChatColor.AQUA + "      Adds acacia_log, removes stone, adds grass_block; in order");
                    player.sendMessage(ChatColor.DARK_AQUA + "    Example: /" + getActiveAlias() + " list dirt- grass_block");
                    player.sendMessage(ChatColor.AQUA + "      Removes dirt, adds grass_block; in order");
                    return true;
                }

                if (args.length == 2 && args[1].equalsIgnoreCase("clear")) {
                    snipeData.getVoxelList().clear();
                    snipeData.getVoxelMessage().voxelList();
                    return true;
                }

                List<String> invalidMaterials = new ArrayList<>();
                for (final String materialString : Arrays.copyOfRange(args, 1, args.length)) {
                    boolean remove = materialString.contains("-");
                    Material material = Material.matchMaterial(materialString.toLowerCase().replace("-", ""));

                    if (material == null || !material.isBlock()) {
                        invalidMaterials.add(materialString.replace("-", ""));
                        continue;
                    }

                    if (!remove) {
                        snipeData.getVoxelList().add(material);
                    } else {
                        snipeData.getVoxelList().remove(material);
                    }
                }

                snipeData.getVoxelMessage().voxelList();

                if (!invalidMaterials.isEmpty()) {
                    player.sendMessage(ChatColor.RED + "Couldn't add because item is non-existent or aren't blocks:- ");
                    player.sendMessage(ChatColor.GOLD + "    " + String.join(", ", invalidMaterials));
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
        if (getActiveAlias().equalsIgnoreCase("vc") || getActiveAlias().equals("vh")) {
            if (args.length == 1) {
                return Lists.newArrayList("[number]");
            }
        }

        if (getActiveAlias().equalsIgnoreCase("vl")) {
            // Preprocess the string for partial matching, strip the '-' if there's one
            args[0] = args[0].toLowerCase().replace("-", "");

            if (!args[0].startsWith("minecraft:")) {
                if (args[0].startsWith("mi") && !args[0].equals("minecraft:")) {
                    return Lists.newArrayList("minecraft:");
                }

                args[0] = "minecraft:" + args[0];
            }

            return getTabCompletion(1);
        }

        if (getActiveIdentifier().equalsIgnoreCase(getIdentifier())) {
            if (args.length == 1) {
                return Lists.newArrayList("list", "center", "height");
            }

            if (args[0].equalsIgnoreCase("center") || args[0].equals("height")) {
                if (args.length == 1) {
                    return Lists.newArrayList("[number]");
                }
            }

            if (args[0].equalsIgnoreCase("list")) {
                // Preprocess the string for partial matching, strip the '-' if there's one
                args[0] = args[0].toLowerCase().replace("-", "");

                if (!args[0].startsWith("minecraft:")) {
                    if (args[0].startsWith("mi") && !args[0].equals("minecraft:")) {
                        return Lists.newArrayList("minecraft:");
                    }

                    args[0] = "minecraft:" + args[0];
                }

                return getTabCompletion(1);
            }
        }

        return new ArrayList<>();
    }
}
