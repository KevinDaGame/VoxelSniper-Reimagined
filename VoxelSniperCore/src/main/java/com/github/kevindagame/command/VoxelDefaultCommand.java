package com.github.kevindagame.command;

import com.github.kevindagame.VoxelProfileManager;
import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;

import java.util.ArrayList;
import java.util.List;

public class VoxelDefaultCommand extends VoxelCommand {

    public VoxelDefaultCommand() {
        super("VoxelDefault");
        setIdentifier("d");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(IPlayer player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);

        // Default command
        // Command: /d info, /d help
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info"))) {
            sniper.sendMessage(Messages.VOXEL_DEFAULT_COMMAND_USAGE.replace("%alias%", getActiveAlias()).replace("%name%", getName()));
            return true;
        }

        if (args.length == 0) {
            sniper.reset(sniper.getCurrentToolId());
            sniper.sendMessage(Messages.BRUSH_SETTINGS_RESET);
            return true;
        }

        return false;
    }

    @Override
    public List<String> doSuggestion(IPlayer player, String[] args) {
        return new ArrayList<>();
    }
}
