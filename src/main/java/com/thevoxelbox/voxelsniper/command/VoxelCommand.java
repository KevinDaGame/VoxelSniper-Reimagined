package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.VoxelCommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class VoxelCommand implements TabExecutor {

    private final String name;
    private String description = "";
    private String permission = "";
    private String identifier = "";
    private final List<String> otherIdentifiers = new ArrayList<>();

    private String activeIdentifier = "";
    private String activeAlias = "";

    public VoxelCommand(String name) {
        this.name = name;
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.activeIdentifier = command.getLabel(); // This is the root command.
        this.activeAlias = label;   // This is the alias that was executed.

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
        this.activeIdentifier = command.getLabel(); // This is the root command.
        this.activeAlias = alias;   // This is the alias that was executed.
        
        // Return partial matches that only match the *beginning* of the string.
        List<String> suggestions = doSuggestion((Player) sender, args); // MUST SPLIT DECLARATION AND ASSIGNMENT, OTHERWISE PARTIAL MATCHING WON'T WORK
        return StringUtil.copyPartialMatches(args[args.length - 1], suggestions, new ArrayList<>());
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

    public final List<String> getOtherIdentifiers() {
        return Collections.unmodifiableList(otherIdentifiers);
    }

    public final void addOtherIdentifiers(String... identifiers) {
        otherIdentifiers.addAll(Arrays.asList(identifiers));
    }

    public final String getActiveIdentifier() {
        return this.activeIdentifier;
    }

    public final String getActiveAlias() {
        return this.activeAlias;
    }

    public List<String> registerTabCompletion() {
        return new ArrayList<>();
    }

    public final List<String> getTabCompletion(int argumentNumber) {
        return getTabCompletion(this.getIdentifier(), argumentNumber);
    }

    public final List<String> getTabCompletion(String identifier, int argumentNumber) {
        return this.getCommandManager().getCommandArgumentsList(identifier, argumentNumber);
    }

    public final VoxelCommandManager getCommandManager() {
        return VoxelCommandManager.getInstance();
    }
}
