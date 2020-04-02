package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.Brushes;
import com.thevoxelbox.voxelsniper.SniperManager;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.brush.IBrush;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ervinnnc
 */
public class VoxelCommandManager {

    private static final List<VoxelCommand> commands = new ArrayList<>();
    private static final HashMap<String, HashMap<Integer, List<String>>> argumentsMap = new HashMap<>();

    public static final String BRUSH_SUBCOMMAND_PREFIX = "brush-";

    public static void registerCommands() {
        commands.clear();
        argumentsMap.clear();

        commands.add(new VoxelBrushCommand());

        for (VoxelCommand command : commands) {
            VoxelSniper.getInstance().getCommand(command.getIdentifier()).setExecutor(command);

            HashMap<Integer, List<String>> commandArgumentsMap = new HashMap<>();

            command.registerTabCompletion(commandArgumentsMap);
            argumentsMap.put(command.getIdentifier(), commandArgumentsMap);
        }
    }

    public static void registerBrushSubcommands() {
        try {
            for (String brushHandle : getBrushManager().getBrushHandles()) {
                IBrush brush = getBrushManager().getBrushForHandle(brushHandle).newInstance();

                HashMap<Integer, List<String>> subcommandArgumentsMap = new HashMap<>();

                brush.registerSubcommandArguments(subcommandArgumentsMap);
                argumentsMap.put(BRUSH_SUBCOMMAND_PREFIX + brushHandle, subcommandArgumentsMap);

                brush.registerArgumentValues(BRUSH_SUBCOMMAND_PREFIX + brushHandle, argumentsMap);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(VoxelCommandManager.class.getName()).log(Level.SEVERE, "Could not initialize brush subcommand arguments!", ex);
        }
    }

    public static Brushes getBrushManager() {
        return VoxelSniper.getInstance().getBrushManager();
    }

    public static SniperManager getSniperManager() {
        return VoxelSniper.getInstance().getSniperManager();
    }

    public static List<String> getCommandArgumentsList(String commandName, int argumentNumber) {
        // If not defined, return an empty list.
        if (!argumentsMap.containsKey(commandName)) {
            return new ArrayList<>();
        }

        List<String> arguments = argumentsMap.get(commandName).getOrDefault(argumentNumber, new ArrayList<>());
        return arguments;
    }

}
