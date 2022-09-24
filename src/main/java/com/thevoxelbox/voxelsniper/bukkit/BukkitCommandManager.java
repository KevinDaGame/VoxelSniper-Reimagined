package com.thevoxelbox.voxelsniper.bukkit;

import com.thevoxelbox.voxelsniper.command.VoxelCommand;
import com.thevoxelbox.voxelsniper.command.VoxelCommandManager;
import org.bukkit.command.PluginCommand;

public final class BukkitCommandManager extends VoxelCommandManager {

//    private final List<VoxelCommand> commands = new ArrayList<>();

    public static void initialize() {
        // Instantiate Command Manager if it's not yet instantiated.
        if (getInstance() == null) {
            instance = new BukkitCommandManager();
        }
    }

    @Override
    public void registerCommand(VoxelCommand command) {
        // Add to local command map for persistence
//        commands.add(command);

        // Initializes the Bukkit-sided registration of the command
        PluginCommand bukkitCommand = BukkitVoxelSniper.getInstance().getCommand(command.getIdentifier());
        argumentsMap.put(command.getIdentifier(), command.registerTabCompletion());

        BukkitCommandHandler executor = new BukkitCommandHandler(command);
        if (bukkitCommand != null) {
            bukkitCommand.setExecutor(executor);
            bukkitCommand.getAliases().forEach(e -> {
                argumentsMap.put(e, command.registerTabCompletion());
            });
        }

        // Initializes command alternates that use the same executors
        command.getOtherIdentifiers().forEach((otherIdentifier) -> {
            PluginCommand bukkitCommandAlt = BukkitVoxelSniper.getInstance().getCommand(otherIdentifier);
            argumentsMap.put(otherIdentifier, command.registerTabCompletion());

            if (bukkitCommandAlt != null) {
                bukkitCommandAlt.setExecutor(executor);
                bukkitCommandAlt.getAliases().forEach(e -> {
                    argumentsMap.put(e, command.registerTabCompletion());
                });
            }
        });
    }
}
