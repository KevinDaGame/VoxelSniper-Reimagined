package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Blend_Brushes
 */
public class BlendVoxelDiscBrush extends BlendBrushBase {

    /**
     *
     */
    public BlendVoxelDiscBrush() {
        this.setName("Blend Voxel Disc");
    }

    @Override
    protected final void blend(final SnipeData v) {
        final int brushSize = v.getBrushSize();
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
                    newMaterials[x][z] = highestMaterial;
                }
            }
        }

        final Undo undo = new Undo();

        // Make the changes
        for (int x = brushSizeDoubled; x >= 0; x--) {
            for (int z = brushSizeDoubled; z >= 0; z--) {
                if (!(this.excludeAir && newMaterials[x][z].isAir()) && !(this.excludeWater && newMaterials[x][z] == VoxelMaterial.WATER)) {
                    this.setBlockMaterialAt(this.getTargetBlock().getX() - brushSize + x, this.getTargetBlock().getY(), this.getTargetBlock().getZ() - brushSize + z, newMaterials[x][z], undo);
                }
            }
        }

        v.owner().storeUndo(undo);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.BALL_BLEND_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        super.parseParameters(triggerHandle, params, v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.blendvoxeldisc";
    }
}