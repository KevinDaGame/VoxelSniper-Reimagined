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
public class pMaterialNoPhysics extends vPerformer {

    private VoxelMaterial voxelMaterial;

    public pMaterialNoPhysics() {
        name = "Material, No Physics";
    }

    @Override
    public void init(com.github.kevindagame.snipe.SnipeData v) {
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
            b.setBlockData(voxelMaterial.createBlockData(), false);
        }
    }
}
