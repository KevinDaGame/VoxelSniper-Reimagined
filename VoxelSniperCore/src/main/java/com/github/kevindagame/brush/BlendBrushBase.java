package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Monofraps
 */
public abstract class BlendBrushBase extends AbstractBrush {

    protected boolean excludeAir = true;
    protected boolean excludeWater = true;

    /**
     * @param v
     */
    protected abstract void blend(final SnipeData v);

    @Override
    protected final void arrow(final SnipeData v) {
        this.excludeAir = false;
        this.blend(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.excludeAir = true;
        this.blend(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        vm.custom(Messages.BLEND_BRUSH_WATER_MODE.replace("%excludeWater%", (this.excludeWater ? "exclude" : "include")));
    }

    @Override
    public void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("water")) {
            if (params.length >= 2) {
                this.excludeWater = !Boolean.parseBoolean(params[1].toLowerCase());
            } else {
                this.excludeWater = !this.excludeWater;
            }
            v.sendMessage(Messages.BLEND_BRUSH_WATER_MODE.replace("%excludeWater%", (this.excludeWater ? "exclude" : "include")));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {
        return new ArrayList<>(Lists.newArrayList("water"));
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();


        argumentValues.put("water", Lists.newArrayList("true", "false"));

        return argumentValues;
    }

    /**
     * @return
     */
    protected final boolean isExcludeAir() {
        return excludeAir;
    }

    /**
     * @param excludeAir
     */
    protected final void setExcludeAir(boolean excludeAir) {
        this.excludeAir = excludeAir;
    }

    /**
     * @return
     */
    protected final boolean isExcludeWater() {
        return excludeWater;
    }

    /**
     * @param excludeWater
     */
    protected final void setExcludeWater(boolean excludeWater) {
        this.excludeWater = excludeWater;
    }
}
