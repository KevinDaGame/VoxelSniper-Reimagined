/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.util.VoxelList;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public class pIncludeMat extends vPerformer {

    private VoxelList includeList;
    private Material voxelMaterial;

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
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        includeList = v.getVoxelList();
    }

    @Override
    public void perform(Block b) {
        if (includeList.contains(b.getType())) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData());
        }
    }
}
