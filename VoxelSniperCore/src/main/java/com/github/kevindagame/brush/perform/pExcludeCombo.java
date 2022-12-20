/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BrushOperation.BlockOperation;
import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

/**
 * @author Voxel
 */
public class pExcludeCombo extends BasePerformer {

    private VoxelList excludeList;
    private IBlockData voxelSubstance;

    public pExcludeCombo() {
        name = "Exclude Combo";
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxelList();
        vm.voxel();
        vm.data();
    }

    @Override
    public void init(SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
        excludeList = v.getVoxelList();
    }

    @Override
    public boolean test(IBlock b) {
        return !excludeList.contains(b.getMaterial());
    }

    @Override
    public BlockOperation perform(IBlock b) {
        return new BlockOperation(b.getLocation(), b.getBlockData(), voxelSubstance);
    }
}
