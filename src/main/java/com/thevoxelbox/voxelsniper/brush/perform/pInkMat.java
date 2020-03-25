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
public class pInkMat extends vPerformer
{

    private byte d;
    private int ir;

    public pInkMat()
    {
        name = "Ink-Mat";
    }

    @Override
    public void init(com.thevoxelbox.voxelsniper.SnipeData v)
    {
        w = v.getWorld();
        d = v.getData();
        ir = v.getReplaceId();
    }

    @Override
    public void info(Message vm)
    {
        vm.performerName(name);
        vm.data();
        vm.replace();
    }

    @SuppressWarnings("deprecation")
	@Override
    public void perform(Block b)
    {
        if (MagicValues.getIdFor(b.getType()) == ir)
        {
            h.put(b);
            b.setBlockData(MagicValues.getBlockDataFor(MagicValues.getIdFor(b.getType()), d), true);
        }
    }

    @Override
    public boolean isUsingReplaceMaterial()
    {
        return true;
    }
}
