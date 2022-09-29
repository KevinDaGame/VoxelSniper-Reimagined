/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * @author Voxel
 */
public class pMatMat extends vPerformer {

    private VoxelMaterial voxelMaterial;
    private VoxelMaterial targetMaterial;

    public pMatMat() {
        name = "Mat-Mat";
    }

    @Override
    public void init(com.github.kevindagame.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        targetMaterial = v.getReplaceMaterial();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
    }

    @Override
    public void perform(IBlock b) {
        if (b.getMaterial() == targetMaterial) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData());
        }
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}