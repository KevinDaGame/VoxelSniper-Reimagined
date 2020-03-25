/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.MagicValues;
import com.thevoxelbox.voxelsniper.Message;

import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public class pComboNoUndo extends vPerformer
{

    private int i;
    private byte d;

    public pComboNoUndo()
    {
        name = "Combo, No-Undo"; // made name more descriptive - Giltwist
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.SnipeData v)
    {
        w = v.getWorld();
        i = v.getVoxelId();
        d = v.getData();
    }

    @Override
    public void info(Message vm)
    {
        vm.performerName(name);
        vm.voxel();
        vm.data();
    }

    @SuppressWarnings("deprecation")
	@Override
    public void perform(Block b)
    {
        if (MagicValues.getIdFor(b.getType()) != i || b.getData() != d)
        {
            b.setBlockData(MagicValues.getBlockDataFor(i, d), true);
        }
    }
}