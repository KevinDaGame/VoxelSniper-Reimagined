package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.IPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class VoxelCommand {

    private final String name;
    private final List<String> otherIdentifiers = new ArrayList<>();
    private String description = "";
    private String permission = "";
    private String identifier = "";
    private String activeIdentifier = "";
    private String activeAlias = "";

    public VoxelCommand(String name) {
        this.name = name;
    }

    public boolean execute(IPlayer player, String[] args) {
        if (getPermission() == null || getPermission().isEmpty() || player.hasPermission(getPermission())) {
            return doCommand(player, args);
        } else {
            player.sendMessage(Messages.NO_PERMISSION_MESSAGE.replace("%permission%", getPermission()));
            return true;
        }
    }

    public abstract boolean doCommand(IPlayer player, String[] args);

    public abstract List<String> doSuggestion(IPlayer player, String[] args);

    public final String getDescription() {
        return description;
    }

    protected final void setDescription(String description) {
        this.description = description;
    }

    public final String getPermission() {
        return this.permission;
    }

    protected final void setPermission(String permission) {
        this.permission = permission;
    }

    public final String getName() {
        return this.name;
    }

    public final String getIdentifier() {
        return this.identifier;
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

    public void setActiveIdentifier(String activeIdentifier) {
        this.activeIdentifier = activeIdentifier;
    }

    public final String getActiveAlias() {
        return this.activeAlias;
    }

    public void setActiveAlias(String activeAlias) {
        this.activeAlias = activeAlias;
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
