package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.BukkitPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(new BukkitPlayer(player));
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());

        if (getActiveAlias().equalsIgnoreCase("vc")) {
            try {
                snipeData.setcCen(Integer.parseInt(args[0]));
                snipeData.getVoxelMessage().center();
                return true;
            } catch (NumberFormatException exception) {
                sniper.sendMessage(Messages.VOXEL_CENTER_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
                return true;
            }
        }

        if (getActiveAlias().equalsIgnoreCase("vh")) {
            try {
                snipeData.setVoxelHeight(Integer.parseInt(args[0]));
                snipeData.getVoxelMessage().height();
                return true;
            } catch (NumberFormatException exception) {
                sniper.sendMessage(Messages.VOXEL_HEIGHT_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
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
                sniper.sendMessage(Messages.VOXEL_LIST_CLEAR_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
                return true;
            }

            List<String> invalidMaterials = new ArrayList<>();
            for (final String string : args) {
                checkMaterial(string, invalidMaterials, snipeData);
            }

            snipeData.getVoxelMessage().voxelList();

            if (!invalidMaterials.isEmpty()) {
                sniper.sendMessage(Messages.VOXEL_LIST_COULDNT_ADD.replace("%blocks%", String.join(", ", invalidMaterials)));
            }
            return true;
        }

        // Default command
        // Command: /vir info, /vir help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            sniper.sendMessage(Messages.VOXEL_VARIABLE_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
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
                    sniper.sendMessage(Messages.VOXEL_LIST_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
                    return true;
                }

                if (args.length == 2 && args[1].equalsIgnoreCase("clear")) {
                    snipeData.getVoxelList().clear();
                    snipeData.getVoxelMessage().voxelList();
                    return true;
                }

                List<String> invalidMaterials = new ArrayList<>();
                for (final String materialString : Arrays.copyOfRange(args, 1, args.length)) {
                    checkMaterial(materialString, invalidMaterials, snipeData);
                }

                snipeData.getVoxelMessage().voxelList();

                if (!invalidMaterials.isEmpty()) {
                    sniper.sendMessage(Messages.VOXEL_LIST_COULDNT_ADD.replace("%blocks%", String.join(", ", invalidMaterials)));
                }
                return true;
            }
        }

        return false;
    }

    private void checkMaterial(String string, List<String> invalidMaterials, SnipeData snipeData) {
        boolean remove = string.contains("-");
        VoxelMaterial material = new VoxelMaterial(string.toLowerCase().replace("-", ""));
        //if the material is not yet defined in VoxelMaterial means that it is an item and not a block
        if (VoxelMaterial.getMaterial(material.getKey()) == null) {
            invalidMaterials.add(string.replace("-", ""));
            return;
        }

        if (!remove) {
            snipeData.getVoxelList().add(material);
        } else {
            snipeData.getVoxelList().remove(material);
        }
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
