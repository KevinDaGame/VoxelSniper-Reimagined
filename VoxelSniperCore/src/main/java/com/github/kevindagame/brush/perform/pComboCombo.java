/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.operation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

/**
 * @author Voxel
 */
public class pComboCombo extends BasePerformer {

    private IBlockData voxelSubstance;
    private IBlockData targetSubstance;

    public pComboCombo() {
        name = "Combo-Combo";
    }

    @Override
    public void init(SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
        targetSubstance = v.getReplaceSubstance();
    }

    @Override
    public boolean test(IBlock b) {
        return b.getBlockData().matches(targetSubstance);
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
    public BlockOperation perform(IBlock b) {
        return new BlockOperation(b.getLocation(), b.getBlockData(), voxelSubstance);
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
