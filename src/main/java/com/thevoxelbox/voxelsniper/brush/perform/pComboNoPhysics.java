/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.Message;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * @author Voxel
 */
public class pComboNoPhysics extends vPerformer {

    private BlockData voxelSubstance;

    public pComboNoPhysics() {
        name = "Combo, No Physics";
    }

    @Override
    public void info(Message vm) {
        vm.performerName(name);
        vm.voxel();
        vm.data();
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void perform(Block b) {
        h.put(b);
        b.setBlockData(voxelSubstance, false);
    }
}
