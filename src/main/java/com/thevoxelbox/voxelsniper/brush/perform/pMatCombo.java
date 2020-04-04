/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * @author Voxel
 */
public class pMatCombo extends vPerformer {

    private Material voxelMaterial;
    private BlockData targetSubstance;

    public pMatCombo() {
        name = "Mat-Combo";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        targetSubstance = v.getReplaceSubstance();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
        vm.replaceData();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void perform(Block b) {
        if (b.getBlockData().matches(targetSubstance)) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData(), true);
        }
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
