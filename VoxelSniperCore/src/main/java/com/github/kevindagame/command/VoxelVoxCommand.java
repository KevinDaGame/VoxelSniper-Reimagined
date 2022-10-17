package com.github.kevindagame.command;

import com.google.common.collect.Lists;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ervinnnc
 */
public class VoxelVoxCommand extends VoxelCommand {

    public VoxelVoxCommand() {
        super("Vox Utility");
        setIdentifier("vox");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
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
                    player.sendMessage(Messages.PAINTING_INVALID_SYNTAX);
                }
                return true;
            }
        }

        // Command: /vchunk
        if (getActiveAlias().equalsIgnoreCase("vchunk")) {
            player.getWorld().refreshChunk(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
            player.sendMessage(Messages.REFRESHED_CHUNK);
            return true;
        }

        // Command: /goto
        if (getActiveAlias().equalsIgnoreCase("goto")) {
            try {
                final int x = Integer.parseInt(args[0]);
                final int z = Integer.parseInt(args[1]);

                player.teleport(new VoxelLocation(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z));
                player.sendMessage(Messages.GOTO_MSG);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                player.sendMessage(Messages.GOTO_INVALID_SYNTAX);
            }
            return true;
        }

        // Default command
        // Command: /vox, /vox help, /vox info
        if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")))) {
            player.sendMessage(Messages.VOX_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
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
                    player.sendMessage(Messages.VOX_PAINTING_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
                }
                return true;
            }
        }

        // Command: /vox chunk
        if (args[0].equalsIgnoreCase("chunk")) {
            player.getWorld().refreshChunk(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
            player.sendMessage(Messages.REFRESHED_CHUNK);
            return true;
        }

        // Command: /vox goto <x> <z>
        if (args[0].equalsIgnoreCase("goto")) {
            try {
                final int x = Integer.parseInt(args[1]);
                final int z = Integer.parseInt(args[2]);

                player.teleport(new VoxelLocation(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z));
                player.sendMessage(Messages.GOTO_MSG);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                player.sendMessage(Messages.VOX_GOTO_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
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

}
