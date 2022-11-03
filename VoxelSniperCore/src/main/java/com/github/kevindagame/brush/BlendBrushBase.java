package com.github.kevindagame.brush;

import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Monofraps
 */
public abstract class BlendBrushBase extends AbstractBrush {

    protected boolean excludeAir = true;
    protected boolean excludeWater = true;

    /**
     * @param v
     */
    protected abstract void blend(SnipeData v);
    protected VoxelMaterial[][][] blend3D(int brushSize) {
        final int brushSizeDoubled = 2 * brushSize;
        // Array that holds the original materials plus a buffer
        final VoxelMaterial[][][] oldMaterials = new VoxelMaterial[2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1];
        // Array that holds the blended materials
        final VoxelMaterial[][][] newMaterials = new VoxelMaterial[brushSizeDoubled + 1][brushSizeDoubled + 1][brushSizeDoubled + 1];

        // Log current materials into oldmats
        for (int x = 0; x <= 2 * (brushSize + 1); x++) {
            for (int y = 0; y <= 2 * (brushSize + 1); y++) {
                for (int z = 0; z <= 2 * (brushSize + 1); z++) {
                    oldMaterials[x][y][z] = this.getBlockMaterialAt(this.getTargetBlock().getX() - brushSize - 1 + x, this.getTargetBlock().getY() - brushSize - 1 + y, this.getTargetBlock().getZ() - brushSize - 1 + z);
                }
            }
        }

        // Log current materials into newmats
        for (int x = 0; x <= brushSizeDoubled; x++) {
            for (int y = 0; y <= brushSizeDoubled; y++) {
                System.arraycopy(oldMaterials[x + 1][y + 1], 1, newMaterials[x][y], 0, brushSizeDoubled + 1);
            }
        }

        // Blend materials
        for (int x = 0; x <= brushSizeDoubled; x++) {
            for (int y = 0; y <= brushSizeDoubled; y++) {
                for (int z = 0; z <= brushSizeDoubled; z++) {
                    Map<VoxelMaterial, Integer> materialFrequency = new HashMap<>();

                    boolean tiecheck = true;

                    for (int m = -1; m <= 1; m++) {
                        for (int n = -1; n <= 1; n++) {
                            for (int o = -1; o <= 1; o++) {
                                if (!(m == 0 && n == 0 && o == 0)) {
                                    VoxelMaterial currentMaterial = oldMaterials[x + 1 + m][y + 1 + n][z + 1 + o];
                                    int currentFrequency = materialFrequency.getOrDefault(currentMaterial, 0) + 1;

                                    materialFrequency.put(currentMaterial, currentFrequency);
                                }
                            }
                        }
                    }

                    int highestMaterialCount = 0;
                    VoxelMaterial highestMaterial = VoxelMaterial.AIR;

                    // Find most common neighbouring material
                    for (Map.Entry<VoxelMaterial, Integer> e : materialFrequency.entrySet()) {
                        if (e.getValue() > highestMaterialCount && !(this.excludeAir && e.getKey().isAir()) && !(this.excludeWater && e.getKey() == VoxelMaterial.WATER)) {
                            highestMaterialCount = e.getValue();
                            highestMaterial = e.getKey();
                        }
                    }

                    // Make sure that there's no tie in highest material
                    for (Map.Entry<VoxelMaterial, Integer> e : materialFrequency.entrySet()) {
                        if (e.getValue() == highestMaterialCount && !(this.excludeAir && e.getKey().isAir()) && !(this.excludeWater && e.getKey() == VoxelMaterial.WATER)) {
                            if (e.getKey() == highestMaterial) {
                                continue;
                            }
                            tiecheck = false;
                        }
                    }

                    // Record most common neighbor material for this block
                    if (tiecheck) {
                        newMaterials[x][y][z] = highestMaterial;
                    }
                }
            }
        }
        return newMaterials;
    }

    protected VoxelMaterial[][] blend2D(int brushSize) {
        final int brushSizeDoubled = 2 * brushSize;
        final VoxelMaterial[][] oldMaterials = new VoxelMaterial[2 * (brushSize + 1) + 1][2 * (brushSize + 1) + 1]; // Array that holds the original materials plus a buffer
        final VoxelMaterial[][] newMaterials = new VoxelMaterial[brushSizeDoubled + 1][brushSizeDoubled + 1]; // Array that holds the blended materials

        // Log current materials into oldmats
        for (int x = 0; x <= 2 * (brushSize + 1); x++) {
            for (int z = 0; z <= 2 * (brushSize + 1); z++) {
                oldMaterials[x][z] = this.getBlockMaterialAt(this.getTargetBlock().getX() - brushSize - 1 + x, this.getTargetBlock().getY(), this.getTargetBlock().getZ() - brushSize - 1 + z);
            }
        }

        // Log current materials into newmats
        for (int x = 0; x <= brushSizeDoubled; x++) {
            System.arraycopy(oldMaterials[x + 1], 1, newMaterials[x], 0, brushSizeDoubled + 1);
        }

        // Blend materials
        for (int x = 0; x <= brushSizeDoubled; x++) {
            for (int z = 0; z <= brushSizeDoubled; z++) {
                Map<VoxelMaterial, Integer> materialFrequency = new HashMap<>();

                boolean tiecheck = true;

                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        if (!(m == 0 && n == 0)) {
                            VoxelMaterial currentMaterial = oldMaterials[x + 1 + m][z + 1 + n];
                            int currentFrequency = materialFrequency.getOrDefault(currentMaterial, 0) + 1;

                            materialFrequency.put(currentMaterial, currentFrequency);
                        }
                    }
                }

                int highestMaterialCount = 0;
                VoxelMaterial highestMaterial = VoxelMaterial.AIR;

                // Find most common neighboring material.
                for (Map.Entry<VoxelMaterial, Integer> e : materialFrequency.entrySet()) {
                    if (e.getValue() > highestMaterialCount && !(this.excludeAir && e.getKey().isAir()) && !(this.excludeWater && e.getKey() == VoxelMaterial.WATER)) {
                        highestMaterialCount = e.getValue();
                        highestMaterial = e.getKey();
                    }
                }

                // Make sure that there's no tie in highest material
                for (Map.Entry<VoxelMaterial, Integer> e : materialFrequency.entrySet()) {
                    if (e.getValue() == highestMaterialCount && !(this.excludeAir && e.getKey().isAir()) && !(this.excludeWater && e.getKey() == VoxelMaterial.WATER)) {
                        if (e.getKey() == highestMaterial) {
                            continue;
                        }
                        tiecheck = false;
                    }
                }

                // Record most common neighbor material for this block
                if (tiecheck) {
                    newMaterials[x][z] = highestMaterial;
                }
            }
        }
        return newMaterials;
    }

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
}
