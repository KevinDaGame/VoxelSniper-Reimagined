/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

/**
 * @author Voxel
 */
public class pIncludeCombo extends vPerformer {

    private VoxelList includeList;
    private IBlockData voxelSubstance;

    public pIncludeCombo() {
        name = "Include Combo";
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxelList();
        vm.voxel();
        vm.data();
    }

    @Override
    public void init(com.github.kevindagame.snipe.SnipeData v) {
        w = v.getWorld();
        includeList = v.getVoxelList();
        voxelSubstance = v.getVoxelSubstance();
    }

    @Override
    public void perform(IBlock b) {
        if (includeList.contains(b.getMaterial())) {
            h.put(b);
            b.setBlockData(voxelSubstance, true);
        }
    }
}
