/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

/**
 * @author Voxel
 */
public class pComboComboNoPhysics extends vPerformer {

    private IBlockData voxelSubstance;
    private IBlockData targetSubstance;

    public pComboComboNoPhysics() {
        name = "Combo-Combo, No Physics";
    }

    @Override
    public void init(com.github.kevindagame.snipe.SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
        targetSubstance = v.getReplaceSubstance();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
        vm.data();
        vm.replaceData();
    }

    @Override
    public void perform(IBlock b) {
        if (b.getBlockData().matches(targetSubstance)) {
            h.put(b);
            b.setBlockData(voxelSubstance, false);
        }
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
