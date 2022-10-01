package com.github.kevindagame.command;

import com.google.common.collect.Lists;
import com.github.kevindagame.VoxelProfileManager;
import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.brush.perform.IPerformerBrush;
import com.github.kevindagame.brush.perform.Performer;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;

import java.util.ArrayList;
import java.util.List;

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
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
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
    public List<String> doSuggestion(IPlayer player, String[] args) {
        if (args.length == 1) {
            return getTabCompletion(args.length);
        }

        return new ArrayList<>();
    }
}
