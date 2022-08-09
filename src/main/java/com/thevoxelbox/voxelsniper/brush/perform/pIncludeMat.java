/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.util.VoxelList;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.MaterialFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;

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
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        includeList = v.getVoxelList();
    }

    @Override
    public void perform(IBlock b) {
        if (includeList.contains(b.getMaterial().getVoxelMaterial())) {
            h.put(b);
            b.setBlockData(MaterialFactory.getMaterial(voxelMaterial).createBlockData());
        }
    }
}
