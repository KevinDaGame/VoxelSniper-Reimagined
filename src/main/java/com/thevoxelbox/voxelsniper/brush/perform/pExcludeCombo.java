/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.util.VoxelList;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockdata.IBlockData;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * @author Voxel
 */
public class pExcludeCombo extends vPerformer {

    private VoxelList excludeList;
    private IBlockData voxelSubstance;

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

    @Override
    public void perform(IBlock b) {
        if (!excludeList.contains(b.getMaterial().getVoxelMaterial())) {
            h.put(b);
            b.setBlockData(voxelSubstance, true);
        }
    }
}
