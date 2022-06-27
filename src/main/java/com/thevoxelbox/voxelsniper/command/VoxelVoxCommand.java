package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.util.BlockHelper;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.ComponentLike;

/**
 *
 * @author ervinnnc
 */
public class VoxelVoxCommand extends VoxelCommand {

    public VoxelVoxCommand() {
        super("Vox Utility");
        setIdentifier("vox");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        // Command: /painting
        if (getActiveAlias().equalsIgnoreCase("painting")) {
            if (args.length == 0) {
                BlockHelper.painting(player, true, false, 0);
                return true;
            }

            if (args.length == 1) {
                try {
                    BlockHelper.painting(player, false, false, Integer.parseInt(args[0]));
                } catch (NumberFormatException e) {
                            sendMessage(player, Messages.PAINTING_INVALID_SYNTAX);
                }
                return true;
            }
        }

        // Command: /vchunk
        if (getActiveAlias().equalsIgnoreCase("vchunk")) {
            player.getWorld().refreshChunk(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
            sendMessage(player, Messages.REFRESHED_CHUNK);
            return true;
        }

        // Command: /goto
        if (getActiveAlias().equalsIgnoreCase("goto")) {
            try {
                final int x = Integer.parseInt(args[0]);
                final int z = Integer.parseInt(args[1]);

                player.teleport(new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z));
                sendMessage(player, Messages.GOTO_MSG);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                sendMessage(player, Messages.GOTO_INVALID_SYNTAX);
            }
            return true;
        }

        // Default command
        // Command: /vox, /vox help, /vox info
        if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")))) {
            player.sendMessage(ChatColor.DARK_AQUA + getName() + " Command Syntax:");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " chunk");
            player.sendMessage(ChatColor.YELLOW + "    Force refreshes the chunk that you are standing in.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " painting");
            player.sendMessage(ChatColor.YELLOW + "    Changes the painting you are looking at.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " painting [number]");
            player.sendMessage(ChatColor.YELLOW + "    Changes the painting you are looking at to a specified ID.");
            return true;
        }

        // Command: /vox painting
        if (args[0].equalsIgnoreCase("painting")) {
            if (args.length == 1) {
                BlockHelper.painting(player, true, false, 0);
                return true;
            }

            // Command: /vox painting [number]
            if (args.length == 2) {
                try {
                    BlockHelper.painting(player, false, false, Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid syntax. Command: /" + getActiveAlias() + " painting [number]");
                }
                return true;
            }
        }

        // Command: /vox chunk
        if (args[0].equalsIgnoreCase("chunk")) {
            player.getWorld().refreshChunk(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
            sendMessage(player, Messages.REFRESHED_CHUNK);
            return true;
        }

        // Command: /vox goto <x> <z>
        if (args[0].equalsIgnoreCase("goto")) {
            try {
                final int x = Integer.parseInt(args[1]);
                final int z = Integer.parseInt(args[2]);

                player.teleport(new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z));
                sendMessage(player, Messages.GOTO_MSG);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                player.sendMessage(ChatColor.RED + "Invalid syntax. Command:" + ChatColor.GOLD + "/" + getActiveAlias() + " goto <x> <z>");
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
        if (getActiveAlias().equalsIgnoreCase("painting")) {
            if (args.length == 1) {
                return Lists.newArrayList("[number]");
            }
        }

        if (getActiveAlias().equalsIgnoreCase("goto")) {
            if (args.length <= 2) {
                return Lists.newArrayList("[number]");
            }
        }

        if (getActiveIdentifier().equalsIgnoreCase("vox")) {
            if (args.length == 1) {
                return Lists.newArrayList("goto", "painting", "chunk");
            }

            if (args[0].equalsIgnoreCase("painting")) {
                if (args.length == 2) {
                    return Lists.newArrayList("[number]");
                }
            }

            if (args[0].equalsIgnoreCase("goto")) {
                if (args.length <= 3) {
                    return Lists.newArrayList("[number]");
                }
            }
        }

        return new ArrayList<>();
    }

    private static void sendMessage(Player p, ComponentLike msg) {
        VoxelSniper.getAdventure().player(p).sendMessage(msg);
    }

}
