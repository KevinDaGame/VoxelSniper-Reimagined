package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Blend_Brushes
 */
public class BlendBallBrush extends BlendBrushBase {

    /**
     * TODO: NEEDS TO CHECK AFTER REWRITE
     */
    public BlendBallBrush() {
        this.setName("Blend Ball");
    }

    @Override
    protected final void blend(final SnipeData v) {
        final int brushSize = v.getBrushSize();
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
                    for (Entry<VoxelMaterial, Integer> e : materialFrequency.entrySet()) {
                        if (e.getValue() > highestMaterialCount && !(this.excludeAir && e.getKey().isAir()) && !(this.excludeWater && e.getKey() == VoxelMaterial.WATER)) {
                            highestMaterialCount = e.getValue();
                            highestMaterial = e.getKey();
                        }
                    }

                    // Make sure that there's no tie in highest material
                    for (Entry<VoxelMaterial, Integer> e : materialFrequency.entrySet()) {
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

        final Undo undo = new Undo();
        final double rSquared = Math.pow(brushSize + 1, 2);

        // Make the changes  
        for (int x = brushSizeDoubled; x >= 0; x--) {
            final double xSquared = Math.pow(x - brushSize - 1, 2);

            for (int y = 0; y <= brushSizeDoubled; y++) {
                final double ySquared = Math.pow(y - brushSize - 1, 2);

                for (int z = brushSizeDoubled; z >= 0; z--) {
                    if (xSquared + ySquared + Math.pow(z - brushSize - 1, 2) <= rSquared) {
                        if (!(this.excludeAir && newMaterials[x][y][z].isAir()) && !(this.excludeWater && (newMaterials[x][y][z] == VoxelMaterial.WATER))) {
                            this.setBlockMaterialAt(this.getTargetBlock().getX() - brushSize + x, this.getTargetBlock().getY() - brushSize + y, this.getTargetBlock().getZ() - brushSize + z, newMaterials[x][y][z], undo);
                        }
                    }
                }
            }
        }
        v.owner().storeUndo(undo);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.BLEND_BALL_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        super.parseParameters(triggerHandle, params, v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.blendball";
    }
}
