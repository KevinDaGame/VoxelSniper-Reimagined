/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterialType;

/**
 * @author Voxel
 */
public class pMatMatNoPhysics extends BasePerformer {

    private VoxelMaterialType voxelMaterialType;
    private VoxelMaterialType targetMaterial;

    public pMatMatNoPhysics() {
        name = "Mat-Mat, No Physics";
    }

    @Override
    public void init(SnipeData v) {
        w = v.getWorld();
        voxelMaterialType = v.getVoxelMaterial();
        targetMaterial = v.getReplaceMaterial();
    }

    @Override
    public boolean test(IBlock b) {
        return b.getMaterial() == targetMaterial;
    }

    @Override
    public void info(VoxelMessage vm) {
        vm.performerName(name);
        vm.voxel();
        vm.replace();
    }

    @Override
    public BlockOperation perform(IBlock b) {
        return new BlockOperation(b.getLocation(), b.getBlockData(), voxelMaterialType.createBlockData(), false);
    }

    @Override
    public boolean isUsingReplaceMaterial() {
        return true;
    }
}
