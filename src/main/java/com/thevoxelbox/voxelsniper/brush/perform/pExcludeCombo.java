/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.util.VoxelList;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * @author Voxel
 */
public class pExcludeCombo extends vPerformer {

    private VoxelList excludeList;
    private BlockData voxelSubstance;

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
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
        excludeList = v.getVoxelList();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void perform(Block b) {
        if (!excludeList.contains(b.getType())) {
            h.put(b);
            b.setBlockData(voxelSubstance, true);
        }
    }
}
