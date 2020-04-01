/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.Message;
import org.bukkit.Material;

import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public class pMatMat extends vPerformer {

    private Material voxelMaterial;
    private Material targetMaterial;

    public pMatMat() {
        name = "Mat-Mat";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        targetMaterial = v.getTargetMaterial();
    }

    @Override
    public void info(Message vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void perform(Block b) {
        if (b.getType() == targetMaterial) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData());
        }
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
