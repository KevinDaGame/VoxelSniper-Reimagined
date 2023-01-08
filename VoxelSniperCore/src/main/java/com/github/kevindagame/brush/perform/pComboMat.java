/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * @author Voxel
 */
public class pComboMat extends BasePerformer {

    private IBlockData voxelSubstance;
    private VoxelMaterial targetMaterial;

    public pComboMat() {
        name = "Combo-Mat";
    }

    @Override
    public void init(SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
        targetMaterial = v.getReplaceMaterial();
    }

    @Override
    public boolean test(IBlock b) {
        return b.getMaterial() == targetMaterial;
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
        vm.data();
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
