/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * @author Voxel
 */
public class pMaterial extends vPerformer {

    private VoxelMaterial voxelMaterial;

    public pMaterial() {
        name = "Material";
    }

    @Override
    public void init(SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
    }

    @Override
    public void perform(IBlock b) {
        if (b.getMaterial() != voxelMaterial) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData());
        }
    }
}
