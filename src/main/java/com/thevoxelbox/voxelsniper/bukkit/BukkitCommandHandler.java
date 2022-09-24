package com.thevoxelbox.voxelsniper.bukkit;

import com.thevoxelbox.voxelsniper.command.VoxelCommand;
import com.thevoxelbox.voxelsniper.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class BukkitCommandHandler implements TabExecutor {
    private final VoxelCommand command;

    public BukkitCommandHandler(VoxelCommand command) {
        this.command = command;
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.command.setActiveIdentifier(command.getLabel()); // This is the root command.
        this.command.setActiveAlias(label);   // This is the alias that was executed.

        if (sender instanceof Player player) {
            return this.command.execute(BukkitVoxelSniper.getInstance().getPlayer(player), args);
        } else {
            BukkitVoxelSniper.getAdventure().sender(sender).sendMessage(Messages.ONLY_PLAYERS_CAN_EXECUTE_COMMANDS);
            return true;
        }
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        this.command.setActiveIdentifier(command.getLabel()); // This is the root command.
        this.command.setActiveAlias(alias);   // This is the alias that was executed.

        if (sender instanceof Player player) {
            // Return partial matches that only match the *beginning* of the string.
            List<String> suggestions = this.command.doSuggestion(BukkitVoxelSniper.getInstance().getPlayer(player), args); // MUST SPLIT DECLARATION AND ASSIGNMENT, OTHERWISE PARTIAL MATCHING WON'T WORK
            return StringUtil.copyPartialMatches(args[args.length - 1], suggestions, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
