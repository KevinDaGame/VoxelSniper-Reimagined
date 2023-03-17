/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.brush.AbstractBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.events.player.PlayerBrushChangedEvent;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Voxel
 */
public abstract class PerformerBrush extends AbstractBrush implements IPerformerBrush {

    protected BasePerformer currentPerformer = new pMaterial();
    protected List<BaseLocation> positions = new ArrayList<>();

    public BasePerformer getCurrentPerformer() {
        return currentPerformer;
    }

    public void sendPerformerMessage(String triggerHandle, SnipeData v) {
        v.sendMessage(Messages.PERFORMER_MESSAGE.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public final boolean parsePerformer(String performerHandle, SnipeData v) {
        BasePerformer newPerfomer = Performer.getPerformer(performerHandle);
        if (newPerfomer != null) {
            if (!new PlayerBrushChangedEvent(v.owner().getPlayer(), v.owner().getCurrentToolId(), this, this).callEvent().isCancelled()) {
                currentPerformer = newPerfomer;
                info(v.getVoxelMessage());
                currentPerformer.info(v.getVoxelMessage());
                return true;
            }
        }
        return false;
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
    public void parseParameters(@NotNull String triggerHandle, @NotNull String[] params, @NotNull SnipeData v) {
        super.parseParameters(triggerHandle, params, v);

        sendPerformerMessage(triggerHandle, v);
    }

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("p"));
    }

    @NotNull
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

    protected abstract void doArrow(SnipeData v);

    protected abstract void doPowder(SnipeData v);

    @Override
    protected final void arrow(SnipeData v) {
        positions.clear();
        doArrow(v);
        addOperations(currentPerformer.perform(positions, v));
    }

    @Override
    protected final void powder(SnipeData v) {
        positions.clear();
        doPowder(v);
        addOperations(currentPerformer.perform(positions, v));
    }
}
