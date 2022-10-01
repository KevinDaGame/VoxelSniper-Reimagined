/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelList;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

/**
 * @author Voxel
 */
public class pIncludeMat extends vPerformer {

    private VoxelList includeList;
    private VoxelMaterial voxelMaterial;

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
        voxelMaterial = v.getVoxelMaterial();
        includeList = v.getVoxelList();
    }

    @Override
    public void perform(IBlock b) {
        if (includeList.contains(b.getMaterial())) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData());
        }
    }
}
