package com.thevoxelbox.voxelsniper.bukkit;

import com.thevoxelbox.voxelsniper.VoxelBrushManager;
import com.thevoxelbox.voxelsniper.brush.IBrush;
import com.thevoxelbox.voxelsniper.command.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;

/**
 *
 * @author ervinnnc
 */
public class VoxelCommandManager {

    private static VoxelCommandManager instance = null;

    public static final String BRUSH_SUBCOMMAND_PREFIX = "brush-";
    public static final String BRUSH_SUBCOMMAND_SUFFIX = "-";

    private final List<VoxelCommand> commands = new ArrayList<>();
    private final HashMap<String, List<String>> argumentsMap = new HashMap<>();

    public static VoxelCommandManager getInstance() {
        return instance;
    }

    public static void initialize() {
        VoxelCommandManager commandManager = getInstance();

        // Instantiate Command Manager if it's not yet instantiated.
        if (commandManager == null) {
            instance = new VoxelCommandManager();
            commandManager = getInstance();
        }

        commandManager.registerCommand(new VoxelBrushCommand());
        commandManager.registerCommand(new VoxelBrushToolCommand());
        commandManager.registerCommand(new VoxelDefaultCommand());
        commandManager.registerCommand(new VoxelInkCommand());
        commandManager.registerCommand(new VoxelInkReplaceCommand());
        commandManager.registerCommand(new VoxelPerformerCommand());
        commandManager.registerCommand(new VoxelReplaceCommand());
        commandManager.registerCommand(new VoxelSniperCommand());
        commandManager.registerCommand(new VoxelUndoCommand());
        commandManager.registerCommand(new VoxelVariablesCommand());
        commandManager.registerCommand(new VoxelVoxCommand());
        commandManager.registerCommand(new VoxelVoxelCommand());

        commandManager.registerBrushSubcommands();
    }

    public void registerCommand(VoxelCommand command) {
        // Add to local command map for persistence
        commands.add(command);

        // Initializes the Bukkit-sided registration of the command
        PluginCommand bukkitCommand = BukkitVoxelSniper.getInstance().getCommand(command.getIdentifier());
        argumentsMap.put(command.getIdentifier(), command.registerTabCompletion());

        bukkitCommand.setExecutor(command);
        bukkitCommand.getAliases().stream().forEach(e -> {
            argumentsMap.put(e, command.registerTabCompletion());
        });

        // Initializes command alternates that use the same executors
        command.getOtherIdentifiers().forEach((otherIdentifier) -> {
            PluginCommand bukkitCommandAlt = BukkitVoxelSniper.getInstance().getCommand(otherIdentifier);
            argumentsMap.put(otherIdentifier, command.registerTabCompletion());

            bukkitCommandAlt.setExecutor(command);
            bukkitCommand.getAliases().stream().forEach(e -> {
                argumentsMap.put(e, command.registerTabCompletion());
            });
        });
    }

    public void registerBrushSubcommands() {
        try {
            for (String brushHandle : VoxelBrushManager.getInstance().getBrushHandles()) {
                // Initialize brush to retrieve subcommand map
                IBrush brush = VoxelBrushManager.getInstance().getBrushForHandle(brushHandle).newInstance();

                if (argumentsMap.containsKey(BRUSH_SUBCOMMAND_PREFIX + brushHandle)) {
                    BukkitVoxelSniper.getInstance().getLogger().log(Level.WARNING, "Did not add clashing argument map: {0}, Brush handle: {1}", new Object[]{BRUSH_SUBCOMMAND_PREFIX + brushHandle, brushHandle});
                    return;
                }

                argumentsMap.put(BRUSH_SUBCOMMAND_PREFIX + brushHandle, brush.registerArguments());

                brush.registerArgumentValues().forEach((identifier, arguments) -> {
                    if (argumentsMap.containsKey(BRUSH_SUBCOMMAND_PREFIX + brushHandle + BRUSH_SUBCOMMAND_SUFFIX + identifier)) {
                        BukkitVoxelSniper.getInstance().getLogger().log(Level.WARNING, "Did not add clashing argument map: {0}, Brush handle: {1}", new Object[]{BRUSH_SUBCOMMAND_PREFIX + brushHandle + identifier, brushHandle});
                        return;
                    }

                    argumentsMap.put(BRUSH_SUBCOMMAND_PREFIX + brushHandle + BRUSH_SUBCOMMAND_SUFFIX + identifier, arguments);
                });
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(VoxelCommandManager.class.getName()).log(Level.SEVERE, "Could not initialize brush subcommand arguments!", ex);
        }
    }

    public List<String> getCommandArgumentsList(String commandName, int argumentNumber) {
        // If not defined, return an empty list.
        if (!argumentsMap.containsKey(commandName)) {
            return new ArrayList<>();
        }

        return argumentsMap.getOrDefault(commandName, new ArrayList<>());
    }
}
