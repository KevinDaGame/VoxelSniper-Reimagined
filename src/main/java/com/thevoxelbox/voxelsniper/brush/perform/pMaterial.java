/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.IMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.MaterialFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public class pMaterial extends vPerformer {

    private VoxelMaterial voxelMaterial;

    public pMaterial() {
        name = "Material";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        w = v.getWorld();
        voxelMaterial = v.getVoxelMaterial();
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
    }

    @Override
    public void perform(IBlock b) {
        if (b.getMaterial() != voxelMaterial) {
            h.put(b);
            b.setBlockData(MaterialFactory.getMaterial(voxelMaterial).createBlockData());
        }
    }
}
