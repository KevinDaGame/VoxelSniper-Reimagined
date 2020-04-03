package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.VoxelCommandManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.brush.IBrush;
import com.thevoxelbox.voxelsniper.brush.perform.Performer;
import com.thevoxelbox.voxelsniper.event.SniperBrushChangedEvent;
import com.thevoxelbox.voxelsniper.event.SniperBrushSizeChangedEvent;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.util.StringUtil;
import com.thevoxelbox.voxelsniper.brush.perform.IPerformer;

public class VoxelBrushCommand extends VoxelCommand {
    
    public VoxelBrushCommand() {
        super("VoxelBrush");
        setIdentifier("b");
        setPermission("voxelsniper.sniper");
    }
    
    @Override
    public void registerTabCompletion(HashMap<Integer, List<String>> argumentListMap) {
        List<String> brushArguments = new ArrayList<>(VoxelCommandManager.getBrushManager().getBrushHandles());
        brushArguments.add("<brushSize>");
        
        argumentListMap.put(1, brushArguments);
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelCommandManager.getSniperManager().getSniperForPlayer(player);
        String currentToolId = sniper.getCurrentToolId();
        SnipeData snipeData = sniper.getSnipeData(currentToolId);

        // No arguments -> get previous brush
        if (args == null || args.length == 0) {
            sniper.previousBrush(currentToolId);
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
            } catch (NumberFormatException exception) {
            }
        }

        if (args.length > 0) {
            // Command: /b list -- list all brushes
            if (args[0].equals("list")) {
                // TODO: LIST BRUSHES
                return true;
            }

            // Command: /b <brush> -- change brush to <brush>
            Class<? extends IBrush> brush = VoxelCommandManager.getBrushManager().getBrushForHandle(args[0]);

            if (brush == null) {
                player.sendMessage("No brush exists with the brush handle '" + args[0] + "'.");
            } else {
                IBrush oldBrush = sniper.getBrush(currentToolId);
                IBrush newBrush = sniper.setBrush(currentToolId, brush);

                // Command: /b <brush> <performer> <...> -- Handles performer and additional variables
                if (args.length > 1) {
                    String[] additionalParameters = Arrays.copyOfRange(args, 1, args.length);
                    
                    // Parse performer if the brush is a performer
                    if (newBrush instanceof IPerformer) {
                        ((IPerformer) newBrush).parsePerformer(args[0], additionalParameters, snipeData);
                        return true;
                    } else {
                        newBrush.parseParameters(args[0], additionalParameters, snipeData);
                        return true;
                    }
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
        
        if (args.length == 2) {
            return getTabCompletion(VoxelCommandManager.BRUSH_SUBCOMMAND_PREFIX + args[0], 1);
        }
        
        if (args.length > 2) {
            return getTabCompletion(VoxelCommandManager.BRUSH_SUBCOMMAND_PREFIX + args[0] + args[1], args.length - 2);
        }
            
        return new ArrayList<>();
    }
}
