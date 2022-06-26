/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.util.VoxelList;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * @author Voxel
 */
public class pIncludeCombo extends vPerformer {

    private VoxelList includeList;
    private BlockData voxelSubstance;

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
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        includeList = v.getVoxelList();
        voxelSubstance = v.getVoxelSubstance();
    }

    @Override
    public void perform(Block b) {
        if (includeList.contains(b.getType())) {
            h.put(b);
            b.setBlockData(voxelSubstance, true);
        }
    }
}
