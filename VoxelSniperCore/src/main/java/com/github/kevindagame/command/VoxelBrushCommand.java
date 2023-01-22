package com.github.kevindagame.command;

import com.github.kevindagame.VoxelBrushManager;
import com.github.kevindagame.VoxelProfileManager;
import com.github.kevindagame.brush.BrushData;
import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.brush.perform.IPerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.events.player.PlayerBrushChangedEvent;
import com.github.kevindagame.voxelsniper.events.player.PlayerBrushSizeChangedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.kevindagame.command.VoxelCommandManager.BRUSH_SUBCOMMAND_PREFIX;
import static com.github.kevindagame.command.VoxelCommandManager.BRUSH_SUBCOMMAND_SUFFIX;

public class VoxelBrushCommand extends VoxelCommand {

    public VoxelBrushCommand() {
        super("VoxelBrush");
        setIdentifier("b");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public List<String> registerTabCompletion() {
        List<String> brushes = new ArrayList<>(VoxelBrushManager.getInstance().getBrushHandles());
        brushes.add("<brushSize>");

        return brushes;
    }

    @Override
    public boolean doCommand(IPlayer player, final String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
        String currentToolId = sniper.getCurrentToolId();
        SnipeData snipeData = sniper.getSnipeData(currentToolId);

        // Default command
        // Command: /b, /b help, /b info
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            snipeData.sendMessage(Messages.VOXEL_BRUSH_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            return true;
        }

        // No arguments -> show brush settings
        if (args.length == 0) {
            snipeData.sendMessage(Messages.VOXEL_BRUSH_COMMAND_SETTINGS);
            sniper.displayInfo();
            return true;
        }

        // Command: /b <number> -- Change brush size
        try {
            int originalSize = snipeData.getBrushSize();

            var brush = sniper.getBrush(currentToolId);
            if (brush == null) return false;

            if (!new PlayerBrushSizeChangedEvent(player, currentToolId, brush, originalSize, snipeData.getBrushSize()).callEvent().isCancelled()) {
                snipeData.setBrushSize(Integer.parseInt(args[0]));
                snipeData.getVoxelMessage().size();
                return true;

            }
            return false;

        } catch (NumberFormatException ignored) {
        }

        // Command: /b list -- list all brushes
        if (args[0].equals("list")) {
            // TODO: LIST BRUSHES
            return true;
        }

        // Command: /b <brush> -- change brush to <brush>
        BrushData data = VoxelBrushManager.getInstance().getBrushForHandle(args[0]);

        if (data == null) {
            snipeData.sendMessage(Messages.BRUSH_HANDLE_NOT_FOUND.replace("%arg%", args[0]));
        } else {
            IBrush oldBrush = sniper.getBrush(currentToolId);
            IBrush newBrush = sniper.instantiateBrush(data);

            if (newBrush == null) {
                snipeData.sendMessage(Messages.VOXEL_BRUSH_NO_PERMISSION);
                return true;
            }

            // Command: /b <brush> <...> -- Handles additional variables
            if (args.length > 1) {
                String[] additionalParameters = Arrays.copyOfRange(args, 1, args.length);

                // Parse performer if the brush is a performer
                if (newBrush instanceof IPerformerBrush) {
                    ((IPerformerBrush) newBrush).parsePerformer(args[0], additionalParameters, snipeData);
                } else {
                    newBrush.parseParameters(args[0], additionalParameters, snipeData);
                }
            }
            if (!new PlayerBrushChangedEvent(player, currentToolId, oldBrush, newBrush).callEvent().isCancelled()) {
                sniper.setBrush(currentToolId, newBrush);
                sniper.displayInfo();
            }
        }

        return true;

    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        if (args.length == 1) {
            return getTabCompletion(args.length);
        }

        if (args.length >= 2) {
            if (args.length % 2 == 0) {
                return getTabCompletion(BRUSH_SUBCOMMAND_PREFIX + args[0], 1);
            } else {
                return getTabCompletion(BRUSH_SUBCOMMAND_PREFIX + args[0] + BRUSH_SUBCOMMAND_SUFFIX + args[args.length - 2], 1);
            }
        }

        return new ArrayList<>();
    }
}
