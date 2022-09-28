/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;

/**
 * @author Voxel
 */
public class pComboNoPhysics extends vPerformer {

    private IBlockData voxelSubstance;

    public pComboNoPhysics() {
        name = "Combo, No Physics";
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
        b.setBlockData(voxelSubstance, false);
    }
}
