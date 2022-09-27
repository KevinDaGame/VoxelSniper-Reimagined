package com.github.kevindagame.voxelsniper.blockdata.redstoneWire;

import com.github.kevindagame.voxelsniper.blockdata.BukkitBlockData;
import org.bukkit.block.data.type.RedstoneWire;

public class BukkitRedstoneWire extends BukkitBlockData implements IRedstoneWire {
    public BukkitRedstoneWire(RedstoneWire blockData) {
        super(blockData);
    }

    @Override
    public int getPower() {
        return ((RedstoneWire) this.blockData).getPower();
    }

    @Override
    public void setPower(int power) {
        ((RedstoneWire) this.blockData).setPower(power);
    }
}
