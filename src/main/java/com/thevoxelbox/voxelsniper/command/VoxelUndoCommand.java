package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.Bukkit;
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
            sniper.sendMessage(Messages.VOXEL_UNDO_COMMAND_USAGE_START.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            if (getActiveIdentifier().equalsIgnoreCase("u")) {
                sniper.sendMessage(Messages.VOXEL_UNDO_COMMAND_USAGE_UNDO.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            }
            sniper.sendMessage(Messages.VOXEL_UNDO_COMMAND_USAGE_END.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
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
            sniper.sendMessage(Messages.UNDO_USER_PERMISSION);
            return true;
        }

        // Command: /u [playerName]             <- Undo [playerName]'s changes.
        if (args.length == 1 || args.length == 2) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sniper.sendMessage(Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return true;
            }

            sniper = profileManager.getSniperForPlayer(targetPlayer);
            int undoAmount = 1;

            // Command: /u [playerName] [amount]    <- Undo the previous [amount] changes made by [playerName].
            if (args.length == 2) {
                try {
                    undoAmount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sniper.sendMessage(Messages.INVALID_UNDO_AMOUNT);
                    return true;
                }
            }

            Sniper targetSniper = profileManager.getSniperForPlayer(targetPlayer);
            targetSniper.sendMessage(Messages.CHANGES_UNDONE_BY_OTHER);
            int amountChanged = sniper.undo(undoAmount);
            sniper.sendMessage(Messages.UNDID_PLAYER_CHANGES.replace("%player%", sniper.getPlayer().getName()).replace("%amountChanged%", String.valueOf(amountChanged)));
            return true;
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
