/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

/**
 * @author Voxel
 */
public class pComboNoPhysics extends BasePerformer {

    private IBlockData voxelSubstance;

    public pComboNoPhysics() {
        name = "Combo, No Physics";
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.data();
    }

    @Override
    public void init(SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
    }

    @Override
    public boolean test(IBlock b) {
        return true;
    }

    @Override
    public BlockOperation perform(IBlock b) {
        return new BlockOperation(b.getLocation(), b.getBlockData(), voxelSubstance, false);
    }
}
