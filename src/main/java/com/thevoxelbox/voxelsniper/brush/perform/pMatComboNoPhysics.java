/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * @author Voxel
 */
public class pMatComboNoPhysics extends vPerformer {

    private IMaterial voxelMaterial;
    private IBlockData targetSubstance;

    public pMatComboNoPhysics() {
        name = "Mat-Combo, No Physics";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
        targetSubstance = v.getReplaceSubstance();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
        vm.replaceData();
    }

    @Override
    public void perform(IBlock b) {
        if (b.getBlockData().matches(targetSubstance)) {
            h.put(b);
            b.setBlockData(voxelMaterial.createBlockData(), false);
        }
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
