package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.brush.IBrush;
import com.thevoxelbox.voxelsniper.brush.perform.IPerformerBrush;
import com.thevoxelbox.voxelsniper.bukkit.VoxelBrushManager;
import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.event.SniperBrushChangedEvent;
import com.thevoxelbox.voxelsniper.event.SniperBrushSizeChangedEvent;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.BukkitPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.thevoxelbox.voxelsniper.bukkit.VoxelCommandManager.BRUSH_SUBCOMMAND_PREFIX;
import static com.thevoxelbox.voxelsniper.bukkit.VoxelCommandManager.BRUSH_SUBCOMMAND_SUFFIX;

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
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(new BukkitPlayer(player));
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
        if (args.length > 0) {
            try {
                int originalSize = snipeData.getBrushSize();
                snipeData.setBrushSize(Integer.parseInt(args[0]));

                SniperBrushSizeChangedEvent event = new SniperBrushSizeChangedEvent(sniper, currentToolId, originalSize, snipeData.getBrushSize());
                Bukkit.getPluginManager().callEvent(event);

                snipeData.getVoxelMessage().size();
                return true;
            } catch (NumberFormatException ignored) {
            }
        }

        if (args.length > 0) {
            // Command: /b list -- list all brushes
            if (args[0].equals("list")) {
                // TODO: LIST BRUSHES
                return true;
            }

            // Command: /b <brush> -- change brush to <brush>
            Class<? extends IBrush> brush = VoxelBrushManager.getInstance().getBrushForHandle(args[0]);

            if (brush == null) {
                snipeData.sendMessage(Messages.BRUSH_HANDLE_NOT_FOUND.replace("%arg%", args[0]));
            } else {
                IBrush oldBrush = sniper.getBrush(currentToolId);
                IBrush newBrush = sniper.setBrush(currentToolId, brush);

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
                    return true;
                }
                SniperBrushChangedEvent event = new SniperBrushChangedEvent(sniper, currentToolId, oldBrush, newBrush);
                sniper.displayInfo();
            }

            return true;
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(Player player, String[] args) {
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
