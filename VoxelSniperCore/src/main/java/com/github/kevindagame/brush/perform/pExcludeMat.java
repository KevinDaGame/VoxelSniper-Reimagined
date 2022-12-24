/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * @author Voxel
 */
public class pExcludeMat extends BasePerformer {

    private VoxelList excludeList;
    private VoxelMaterial voxelMaterial;

    public pExcludeMat() {
        name = "Exclude Material";
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxelList();
        vm.voxel();
    }

    @Override
    public void init(SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        excludeList = v.getVoxelList();
    }

    @Override
    public boolean test(IBlock b) {
        return !excludeList.contains(b.getMaterial());
    }

    @Override
    public BlockOperation perform(IBlock b) {
        return new BlockOperation(b.getLocation(), b.getBlockData(), voxelMaterial.createBlockData());
    }
}
