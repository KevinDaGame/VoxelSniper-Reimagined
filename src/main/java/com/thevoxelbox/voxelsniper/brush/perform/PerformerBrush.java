/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.brush.Brush;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.event.SniperBrushChangedEvent;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

/**
 * @author Voxel
 */
public abstract class PerformerBrush extends Brush implements IPerformerBrush {

    protected vPerformer currentPerformer = new pMaterial();

    public vPerformer getCurrentPerformer() {
        return currentPerformer;
    }

    public void sendPerformerMessage(String triggerHandle, SnipeData v) {
        v.sendMessage(Messages.PERFORMER_MESSAGE.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public final boolean parsePerformer(String performerHandle, SnipeData v) {
        vPerformer newPerfomer = Performer.getPerformer(performerHandle);
        if (newPerfomer == null) {
            return false;
        } else {
            currentPerformer = newPerfomer;

            SniperBrushChangedEvent event = new SniperBrushChangedEvent(v.owner(), v.owner().getCurrentToolId(), this, this);
            Bukkit.getPluginManager().callEvent(event);

            info(v.getVoxelMessage());
            currentPerformer.info(v.getVoxelMessage());
            return true;
        }
    }

    @Override
    public final void parsePerformer(String triggerHandle, String[] args, SnipeData v) {
        if (args.length > 1 && args[0].equalsIgnoreCase("p")) {
            if (!parsePerformer(args[1], v)) {
                parseParameters(triggerHandle, args, v);
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
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("p"));
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {        // Number variables
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("p", Lists.newArrayList(Performer.getPerformerHandles()));

        return argumentValues;
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
