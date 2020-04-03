package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class VoxelUndoCommand extends VoxelCommand {

    public VoxelUndoCommand() {
        super("VoxelUndo");
        setIdentifier("u");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        VoxelProfileManager sniperManager = VoxelSniper.getInstance().getSniperManager();
        Sniper sniper = sniperManager.getSniperForPlayer(player);

        if ((args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) || args.length > 2) {
            player.sendMessage(ChatColor.GOLD + "VoxelUndo Command");
            player.sendMessage(ChatColor.DARK_BLUE + "/u                    -- Undo the latest changes for yourself.");
            player.sendMessage(ChatColor.DARK_BLUE + "/u [amount]           -- Undo the latest [amount] changes for yourself.");
            player.sendMessage(ChatColor.DARK_BLUE + "/u [player]           -- Undo the latest changes for specified player.");
            player.sendMessage(ChatColor.DARK_BLUE + "/u [player] [amount]  -- Undo the latest [amount] changes for specified player.");
        }

        // Command: /u      <- Undo the latest changes for yourself.
        if (args.length == 0) {
            sniper.undo();
        }

        // Command: /u [amount]         <- Undo the previous [amount] changes for yourself.
        if (args.length == 1) {
            try {
                sniper.undo(Integer.parseInt(args[0]));
                return true;
            } catch (NumberFormatException exception) {
            }
        }

        // Command: /u [playerName]             <- Undo [playerName]'s changes.
        if (args.length == 1 || args.length == 2) {
            try {
                sniper = sniperManager.getSniperForPlayer(Bukkit.getPlayer(args[0]));
                int undoAmount = 1;

                // Command: /u [playerName] [amount]    <- Undo the previous [amount] changes made by [playerName].
                if (args.length == 2) {
                    try {
                        undoAmount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid amount to undo. Value must be a number.");
                    }
                }

                int amountChanged = sniper.undo(undoAmount);
                player.sendMessage(ChatColor.GOLD + "Undid " + sniper.getPlayer().getName() + "'s changes: " + ChatColor.DARK_AQUA + amountChanged + " blocks replaced");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Could not find the player " + ChatColor.GOLD + "'" + args[0] + "'" + ChatColor.RED + ".");
            }
        }
        return true;
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
