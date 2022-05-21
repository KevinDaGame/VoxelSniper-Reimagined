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
public class pComboMatNoPhysics extends vPerformer {

    private BlockData voxelSubstance;
    private Material targetMaterial;

    public pComboMatNoPhysics() {
        name = "Combo-Mat, No Physics";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
        targetMaterial = v.getReplaceMaterial();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
        vm.data();
    }

    @Override
    public void perform(Block b) {
        if (b.getType() == targetMaterial) {
            h.put(b);
            b.setBlockData(voxelSubstance, false);
        }
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
