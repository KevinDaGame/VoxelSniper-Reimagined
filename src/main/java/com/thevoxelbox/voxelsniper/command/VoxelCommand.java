package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.VoxelCommandManager;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public abstract class VoxelCommand implements TabExecutor {

    private final String name;
    private String description = "";
    private String permission = "";
    private String identifier = "";

    public VoxelCommand(String name) {
        this.name = name;
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute commands!");
            return true;
        } else {
            if (command.getPermission() == null || getPermission().isEmpty() || sender.hasPermission(getPermission())) {
                return doCommand((Player) sender, args);
            } else {
                sender.sendMessage("You do not have the '" + getPermission() + "' permission node to do that.");
                return true;
            }
        }
    }

    public abstract boolean doCommand(Player player, String[] args);

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Return partial matches that match *any part* of the string.
        if (this instanceof VoxelVoxelCommand) {
            String namespace = "minecraft:";
            args[0] = args[0].toLowerCase();
            
            if (!args[0].startsWith(namespace)) {
                if (args[0].startsWith("mi") && !args[0].equals(namespace)) {
                    return Lists.newArrayList(namespace);
                }

                args[0] = namespace + args[0];
            }
        }

        // Return partial matches that only match the *beginning* of the string.
        return StringUtil.copyPartialMatches(args[args.length - 1], doSuggestion((Player) sender, args), new ArrayList<>());
    }

    public abstract List<String> doSuggestion(Player player, String[] args);

    public final String getDescription() {
        return description;
    }

    public final String getPermission() {
        return this.permission;
    }

    public final String getName() {
        return this.name;
    }

    public final String getIdentifier() {
        return this.identifier;
    }

    protected final void setDescription(String description) {
        this.description = description;
    }

    protected final void setPermission(String permission) {
        this.permission = permission;
    }

    protected final void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public final boolean isIdentifier(String offered) {
        return this.identifier.isEmpty() || this.identifier.equalsIgnoreCase(offered);
    }

    /**
     * Padds an empty String to the front of the array.
     *
     * @param args Array to pad empty string in front of
     * @return padded array
     */
    protected String[] hackTheArray(String[] args) {
        String[] returnValue = new String[args.length + 1];
        returnValue[0] = "";
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            String arg = args[i];
            returnValue[i + 1] = arg;
        }
        return returnValue;
    }

    public void registerTabCompletion(HashMap<Integer, List<String>> argumentListMap) {
    }

    public final List<String> getTabCompletion(int argumentNumber) {
        return getTabCompletion(this.getIdentifier(), argumentNumber);
    }

    public final List<String> getTabCompletion(String identifier, int argumentNumber) {
        System.out.println("Solving for " + identifier);
        return VoxelCommandManager.getCommandArgumentsList(identifier, argumentNumber);
    }
}
