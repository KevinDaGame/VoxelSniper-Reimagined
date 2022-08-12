/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.util.VoxelList;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

/**
 * @author Voxel
 */
public class pExcludeMat extends vPerformer {

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
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        excludeList = v.getVoxelList();
    }

    @Override
    public void perform(IBlock b) {
        if (!excludeList.contains(b.getMaterial())) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData());
        }
    }
}
