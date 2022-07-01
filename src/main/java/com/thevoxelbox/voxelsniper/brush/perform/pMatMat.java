/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public class pMatMat extends vPerformer {

    private IMaterial voxelMaterial;
    private IMaterial targetMaterial;

    public pMatMat() {
        name = "Mat-Mat";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
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
