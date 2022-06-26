package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VoxelUndoCommand extends VoxelCommand {

    public VoxelUndoCommand() {
        super("VoxelUndo");
        setIdentifier("u");
        addOtherIdentifiers("uu");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        VoxelProfileManager profileManager = VoxelProfileManager.getInstance();
        Sniper sniper = profileManager.getSniperForPlayer(player);

        if ((args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) || args.length > 2) {
            player.sendMessage(ChatColor.DARK_AQUA + getName() + " Command Syntax:");
            if (getActiveIdentifier().equalsIgnoreCase("u")) {
                player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias());
                player.sendMessage(ChatColor.YELLOW + "    Undo latest changes for yourself.");
                player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " [changes]");
                player.sendMessage(ChatColor.YELLOW + "    Undo previous [amount] changes for yourself.");
            }
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " [player]");
            player.sendMessage(ChatColor.YELLOW + "    Undo the latest changes for specified player.");
            player.sendMessage(ChatColor.GOLD + "/" + getActiveAlias() + " [player] [amount]");
            player.sendMessage(ChatColor.YELLOW + "    Undo the previous [amount] changes for specified player.");
            return true;
        }

        // Command: /u      <- Undo the latest changes for yourself.
        if (args.length == 0 && getActiveIdentifier().equalsIgnoreCase("u")) {
            sniper.undo();
            return true;
        }

        // Command: /u [amount]         <- Undo the previous [amount] changes for yourself.
        if (args.length == 1 && getActiveIdentifier().equalsIgnoreCase("u")) {
            try {
                sniper.undo(Integer.parseInt(args[0]));
                return true;
            } catch (NumberFormatException ignored) {
            }
        }

        if (!player.hasPermission("voxelsniper.undouser")) {
            player.sendMessage(ChatColor.RED + "You need the 'voxelsniper.undouser' permission to undo other user's changes.");
            return true;
        }

        // Command: /u [playerName]             <- Undo [playerName]'s changes.
        if (args.length == 1 || args.length == 2) {
            try {
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                assert targetPlayer != null;

                sniper = profileManager.getSniperForPlayer(targetPlayer);
                int undoAmount = 1;

                // Command: /u [playerName] [amount]    <- Undo the previous [amount] changes made by [playerName].
                if (args.length == 2) {
                    try {
                        undoAmount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid amount to undo. Value must be a number.");
                        return true;
                    }
                }

                targetPlayer.sendMessage(ChatColor.LIGHT_PURPLE + "Your changes were undone by someone else.");
                int amountChanged = sniper.undo(undoAmount);
                player.sendMessage(ChatColor.GOLD + "Undid " + sniper.getPlayer().getName() + "'s changes: " + ChatColor.DARK_AQUA + amountChanged + " blocks replaced");
                return true;
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Could not find the player " + ChatColor.GOLD + "'" + args[0] + "'" + ChatColor.RED + ".");
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1 || args.length == 2) {
            suggestions.add("[amount]");
        }

        if (args.length == 1) {
            Bukkit.getOnlinePlayers().stream().map(e -> e.getName()).forEach(suggestions::add);
        }

        return suggestions;
    }
}
