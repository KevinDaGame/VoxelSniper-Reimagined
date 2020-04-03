/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import org.bukkit.Material;

import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public class pMaterialNoPhysics extends vPerformer {

    private Material voxelMaterial;

    public pMaterialNoPhysics() {
        name = "Material, No Physics";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void perform(Block b) {
        if (b.getType() != voxelMaterial) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData(), false);
        }
    }
}
