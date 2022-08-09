/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;

/**
 * @author Voxel
 */
public class pCombo extends vPerformer {

    private IBlockData voxelSubstance;

    public pCombo() {
        name = "Combo";
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.data();
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelSubstance = v.getVoxelSubstance();
    }

    @Override
    public void perform(IBlock b) {
        h.put(b);
        b.setBlockData(voxelSubstance, true);
    }
}
