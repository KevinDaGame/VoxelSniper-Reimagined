/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.brush.Brush;
import com.thevoxelbox.voxelsniper.event.SniperBrushChangedEvent;
import java.util.ArrayList;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;

/**
 * @author Voxel
 */
public abstract class PerformBrush extends Brush implements IPerformer {

    protected vPerformer currentPerformer = new pMaterial();

    public vPerformer getCurrentPerformer() {
        return currentPerformer;
    }
    
    public void sendPerformerMessage(String triggerHandle, SnipeData v) {
        v.sendMessage(ChatColor.DARK_AQUA + "You can use " + ChatColor.YELLOW + "'/b " + triggerHandle + " p [performer]'" + ChatColor.DARK_AQUA + " to change performers now.");
    }

    @Override
    public final void parsePerformer(String triggerHandle, String[] args, SnipeData v) {
        if (args.length > 1 && args[0].equalsIgnoreCase("p")) {
            vPerformer newPerfomer = Performer.getPerformer(args[1]);
            if (newPerfomer == null) {
                parseParameters(triggerHandle, args, v);
            } else {
                currentPerformer = newPerfomer;

                SniperBrushChangedEvent event = new SniperBrushChangedEvent(v.owner(), v.owner().getCurrentToolId(), this, this);
                Bukkit.getPluginManager().callEvent(event);

                info(v.getVoxelMessage());
                currentPerformer.info(v.getVoxelMessage());
            }
        } else {
            parseParameters(triggerHandle, args, v);
        }
    }

    @Override
    public void parseParameters(String triggerHandle, String[] params, SnipeData v) {
        super.parseParameters(triggerHandle, params, v);

        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public void registerSubcommandArguments(HashMap<Integer, List<String>> subcommandArguments) {
        List<String> arguments = subcommandArguments.getOrDefault(1, new ArrayList<>());
        arguments.add("p");

        subcommandArguments.putIfAbsent(1, arguments);
    }

    @Override
    public void registerArgumentValues(String prefix, HashMap<String, HashMap<Integer, List<String>>> argumentValues) {        // Number variables
        HashMap<Integer, List<String>> arguments = new HashMap<>();
        arguments.put(1, Lists.newArrayList(Performer.getPerformerHandles()));

        argumentValues.put(prefix + "p", arguments);
    }

    public void initP(SnipeData v) {
        currentPerformer.init(v);
        currentPerformer.setUndo();
    }

    @Override
    public void showInfo(VoxelMessage vm) {
        currentPerformer.info(vm);
    }

}
