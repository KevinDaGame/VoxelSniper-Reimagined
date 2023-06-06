package com.github.kevindagame.voxelsniperforge.blockdata.redstoneWire;

import com.github.kevindagame.voxelsniper.blockdata.redstoneWire.IRedstoneWire;
import com.github.kevindagame.voxelsniperforge.blockdata.ForgeBlockData;

import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ForgeRedstoneWire extends ForgeBlockData implements IRedstoneWire {

    public ForgeRedstoneWire(BlockState state) {
        super(state);
    }

    @Override
    public int getPower() {
        return get(RedStoneWireBlock.POWER);
    }

    @Override
    public void setPower(int power) {
        set(RedStoneWireBlock.POWER, power);
    }
}
