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
public class pNoUndo extends vPerformer
{

    private int i;

    public pNoUndo()
    {
        name = "BOMB SQUAD";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.SnipeData v)
    {
        w = v.getWorld();
        i = v.getVoxelId();
    }

    @Override
    public void info(Message vm)
    {
        vm.performerName(name);
        vm.voxel();
    }

    @SuppressWarnings("deprecation")
	@Override
    public void perform(Block b)
    {
        if (MagicValues.getIdFor(b.getType()) != i)
        {
            b.setBlockData(MagicValues.getBlockDataFor(i));
        }
    }
}