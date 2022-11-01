/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.voxelsniper.events.player.PlayerBrushChangedEvent;
import com.google.common.collect.Lists;
import com.github.kevindagame.brush.AbstractBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Voxel
 */
public abstract class PerformerBrush extends AbstractBrush implements IPerformerBrush {

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

            PlayerBrushChangedEvent event = new PlayerBrushChangedEvent(v.owner().getPlayer(), v.owner().getCurrentToolId(), this, this);
            event.callEvent();

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
