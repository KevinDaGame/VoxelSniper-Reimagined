/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;

/**
 * @author Voxel
 */
public class pExcludeCombo extends vPerformer {

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
    public void perform(IBlock b) {
        if (!excludeList.contains(b.getMaterial())) {
            h.put(b);
            b.setBlockData(voxelSubstance, true);
        }
    }
}
