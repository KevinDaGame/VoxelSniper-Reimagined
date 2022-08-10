package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.bukkit.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.player.BukkitPlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class VoxelDefaultCommand extends VoxelCommand {

    public VoxelDefaultCommand() {
        super("VoxelDefault");
        setIdentifier("d");
        setPermission("voxelsniper.sniper");
    }

    @Override
    public boolean doCommand(Player player, String[] args) {
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(new BukkitPlayer(player));

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
    public List<String> doSuggestion(Player player, String[] args) {
        return new ArrayList<>();
    }
}
