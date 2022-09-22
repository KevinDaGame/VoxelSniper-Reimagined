package com.thevoxelbox.voxelsniper.command;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.brush.IBrush;
import com.thevoxelbox.voxelsniper.brush.perform.IPerformerBrush;
import com.thevoxelbox.voxelsniper.brush.perform.Performer;
import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.BukkitPlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class VoxelPerformerCommand extends VoxelCommand {

    public VoxelPerformerCommand() {
        super("VoxelPerformer");
        setIdentifier("p");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public List<String> registerTabCompletion() {
        return Lists.newArrayList(Performer.getPerformerHandles());
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(new BukkitPlayer(player));
        SnipeData snipeData = sniper.getSnipeData(sniper.getCurrentToolId());
        
        // Default command
        // Command: /p info, /p help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            sniper.sendMessage(Messages.VOXEL_PERFORMER_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            return true;
        }


        if (args.length == 0) {
            IBrush brush = sniper.getBrush(sniper.getCurrentToolId());
            if (brush instanceof IPerformerBrush) {
                ((IPerformerBrush) brush).parsePerformer("m", snipeData);
            } else {
                sniper.sendMessage(Messages.THE_ACTIVE_BRUSH_IS_NOT_A_PERFORMER_BRUSH);
            }
            return true;
        }

        if (args.length == 1) {
            IBrush brush = sniper.getBrush(sniper.getCurrentToolId());
            if (brush instanceof IPerformerBrush) {
                boolean success = ((IPerformerBrush) brush).parsePerformer(args[0], snipeData);
                if (!success) {
                    sniper.sendMessage(Messages.NO_SUCH_PERFORMER.replace("%arg%", args[0]));
                }
            } else {
                sniper.sendMessage(Messages.THE_ACTIVE_BRUSH_IS_NOT_A_PERFORMER_BRUSH);
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
        
        return new ArrayList<>();
    }
}
