/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.VoxelMessage;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * @author Voxel
 */
public class pCombo extends vPerformer {

    private BlockData voxelSubstance;

    public pCombo() {
        name = "Combo";
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.data();
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void perform(Block b) {
        h.put(b);
        b.setBlockData(voxelSubstance, true);
    }
}
