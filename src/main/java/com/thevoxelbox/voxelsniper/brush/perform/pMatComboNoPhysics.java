/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.material.MaterialFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

/**
 * @author Voxel
 */
public class pMatComboNoPhysics extends vPerformer {

    private VoxelMaterial voxelMaterial;
    private IBlockData targetSubstance;

    public pMatComboNoPhysics() {
        name = "Mat-Combo, No Physics";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        targetSubstance = v.getReplaceSubstance();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
        vm.replaceData();
    }

    @Override
    public void perform(IBlock b) {
        if (b.getBlockData().matches(targetSubstance)) {
            h.put(b);
            b.setBlockData(MaterialFactory.getMaterial(voxelMaterial).createBlockData(), false);
        }
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
