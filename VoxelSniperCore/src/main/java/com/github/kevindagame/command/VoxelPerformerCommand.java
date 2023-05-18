package com.github.kevindagame.command;

import com.github.kevindagame.brush.IBrush;
import com.github.kevindagame.brush.perform.IPerformerBrush;
import com.github.kevindagame.brush.perform.Performer;
import com.github.kevindagame.brush.polymorphic.PolyBrush;
import com.github.kevindagame.brush.polymorphic.property.PerformerProperty;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Utils;
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
        return Utils.newArrayList(Performer.getPerformerHandles());
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = player.getSniper();

        // Default command
        // Command: /p info, /p help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            sniper.sendMessage(Messages.VOXEL_PERFORMER_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            return true;
        }

        IBrush brush = sniper.getBrush(sniper.getCurrentToolId());
        if (args.length == 0) {
            return setPerformer(brush, "m", sniper);
        }

        if (args.length == 1) {
            return setPerformer(brush, args[0], sniper);
        }
        return false;
    }

    private boolean setPerformer(IBrush brush, String performer, Sniper sniper) {
        if (brush instanceof IPerformerBrush) {
            return ((IPerformerBrush) brush).parsePerformer(performer, sniper.getSnipeData(sniper.getCurrentToolId()));
        } else if (brush instanceof PolyBrush) {
            var properties = ((PolyBrush) brush).getProperties();
            for (var property : properties) {
                if (property instanceof PerformerProperty) {
                    (property).set(performer);
                    brush.info(sniper.getSnipeData(sniper.getCurrentToolId()).getVoxelMessage());
                    return true;
                }
            }
            sniper.sendMessage(Messages.THE_ACTIVE_BRUSH_IS_NOT_A_PERFORMER_BRUSH);
            return false;
        } else {
            sniper.sendMessage(Messages.THE_ACTIVE_BRUSH_IS_NOT_A_PERFORMER_BRUSH);
            return false;
        }

    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        if (args.length == 1) {
            return getTabCompletion(args.length);
        }

        return new ArrayList<>();
    }
}
