package com.github.kevindagame.voxelsniper.blockdata.redstoneWire;

import com.github.kevindagame.voxelsniper.blockdata.SpigotBlockData;
import org.bukkit.block.data.type.RedstoneWire;

public class SpigotRedstoneWire extends SpigotBlockData implements IRedstoneWire {
    public SpigotRedstoneWire(RedstoneWire blockData) {
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
