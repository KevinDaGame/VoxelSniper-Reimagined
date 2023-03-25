/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;

/**
 * @author Voxel
 */
public class pIncludeMat extends BasePerformer {

    private VoxelList includeList;
    private VoxelMaterialType voxelMaterialType;

    public pIncludeMat() {
        name = "Include Material";
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
        voxelMaterialType = v.getVoxelMaterial();
        includeList = v.getVoxelList();
    }

    @Override
    public boolean test(IBlock b) {
        return includeList.contains(b.getMaterial());
    }

    @Override
    public BlockOperation perform(IBlock b) {
        return new BlockOperation(b.getLocation(), b.getBlockData(), voxelMaterialType.createBlockData());
    }
}
