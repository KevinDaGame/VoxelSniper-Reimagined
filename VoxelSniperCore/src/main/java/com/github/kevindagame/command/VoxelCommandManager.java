package com.github.kevindagame.command;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.brush.IBrush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * @author ervinnnc
 */
public abstract class VoxelCommandManager {

    public static final String BRUSH_SUBCOMMAND_PREFIX = "brush-";
    public static final String BRUSH_SUBCOMMAND_SUFFIX = "-";
    protected static VoxelCommandManager instance = null;
    protected final HashMap<String, List<String>> argumentsMap = new HashMap<>();

    public VoxelCommandManager() {
        this.registerCommand(new VoxelBrushCommand());
        this.registerCommand(new VoxelBrushToolCommand());
        this.registerCommand(new VoxelDefaultCommand());
        this.registerCommand(new VoxelInkCommand());
        this.registerCommand(new VoxelInkReplaceCommand());
        this.registerCommand(new VoxelPerformerCommand());
        this.registerCommand(new VoxelReplaceCommand());
        this.registerCommand(new VoxelSniperCommand());
        this.registerCommand(new VoxelUndoCommand());
        this.registerCommand(new VoxelVariablesCommand());
        this.registerCommand(new VoxelVoxCommand());
        this.registerCommand(new VoxelVoxelCommand());

        this.registerBrushSubcommands();
    }

    public static VoxelCommandManager getInstance() {
        return instance;
    }

    public abstract void registerCommand(VoxelCommand command);

    public void registerBrushSubcommands() {
        for (String brushHandle : VoxelBrushManager.getInstance().getBrushHandles()) {
            // Initialize brush to retrieve subcommand map
            IBrush brush = VoxelBrushManager.getInstance().getBrushForHandle(brushHandle).getSupplier().get();

            if (argumentsMap.containsKey(BRUSH_SUBCOMMAND_PREFIX + brushHandle)) {
                VoxelSniper.voxelsniper.getLogger().log(Level.WARNING, "Did not add clashing argument map: {0}, Brush handle: {1}", new Object[]{BRUSH_SUBCOMMAND_PREFIX + brushHandle, brushHandle});
                return;
            }

            argumentsMap.put(BRUSH_SUBCOMMAND_PREFIX + brushHandle, brush.registerArguments());

            brush.registerArgumentValues().forEach((identifier, arguments) -> {
                if (argumentsMap.containsKey(BRUSH_SUBCOMMAND_PREFIX + brushHandle + BRUSH_SUBCOMMAND_SUFFIX + identifier)) {
                    VoxelSniper.voxelsniper.getLogger().log(Level.WARNING, "Did not add clashing argument map: {0}, Brush handle: {1}", new Object[]{BRUSH_SUBCOMMAND_PREFIX + brushHandle + identifier, brushHandle});
                    return;
                }

                argumentsMap.put(BRUSH_SUBCOMMAND_PREFIX + brushHandle + BRUSH_SUBCOMMAND_SUFFIX + identifier, arguments);
            });
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
