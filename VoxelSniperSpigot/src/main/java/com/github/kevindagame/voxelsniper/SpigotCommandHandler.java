package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.command.VoxelCommand;
import com.github.kevindagame.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SpigotCommandHandler implements TabExecutor {
    private final VoxelCommand command;

    public SpigotCommandHandler(VoxelCommand command) {
        this.command = command;
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.command.setActiveIdentifier(command.getLabel()); // This is the root command.
        this.command.setActiveAlias(label);   // This is the alias that was executed.

        if (sender instanceof Player player) {
            return this.command.execute(SpigotVoxelSniper.getInstance().getPlayer(player), args);
        } else {
            SpigotVoxelSniper.getAdventure().sender(sender).sendMessage(Messages.ONLY_PLAYERS_CAN_EXECUTE_COMMANDS);
            return true;
        }
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        this.command.setActiveIdentifier(command.getLabel()); // This is the root command.
        this.command.setActiveAlias(alias);   // This is the alias that was executed.

        if (sender instanceof Player player) {
            // Return partial matches that only match the *beginning* of the string.
            List<String> suggestions = this.command.doSuggestion(SpigotVoxelSniper.getInstance().getPlayer(player), args); // MUST SPLIT DECLARATION AND ASSIGNMENT, OTHERWISE PARTIAL MATCHING WON'T WORK
            return StringUtil.copyPartialMatches(args[args.length - 1], suggestions, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
