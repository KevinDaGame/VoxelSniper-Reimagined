/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.MaterialFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

/**
 * @author Voxel
 */
public class pMatMatNoPhysics extends vPerformer {

    private VoxelMaterial voxelMaterial;
    private VoxelMaterial targetMaterial;

    public pMatMatNoPhysics() {
        name = "Mat-Mat, No Physics";
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
            b.setBlockData(MaterialFactory.getMaterial(voxelMaterial).createBlockData(), false);
        }
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
