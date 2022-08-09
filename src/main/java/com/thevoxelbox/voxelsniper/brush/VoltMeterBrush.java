package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Volt-Meter_Brush
 *
 * @author Gavjenks
 */
public class VoltMeterBrush extends Brush {

    /**
     *
     */
    public VoltMeterBrush() {
        this.setName("VoltMeter");
    }

    @SuppressWarnings("deprecation")
    private void data(final SnipeData v) {
        final Block block = this.clampY(this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ());
        final byte data = block.getData();
        v.sendMessage(Messages.BLOCKS_UNTIL_REPEATER_MESSAGE.replace("%blocks%", String.valueOf(data)));
    }

    private void volt(final SnipeData v) {
        final Block block = this.clampY(this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ());
        final boolean indirect = block.isBlockIndirectlyPowered();
        final boolean direct = block.isBlockPowered();
        v.sendMessage(Messages.BLOCK_POWER_MESSAGE.replace("%direct%", String.valueOf(direct)).replace("%indirect%", String.valueOf(indirect)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "Top'").replace("%direct%", ""+block.isBlockFacePowered(BlockFace.UP)).replace("%indirect%", ""+block.isBlockFaceIndirectlyPowered(BlockFace.UP)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "Bottom'").replace("%direct%", ""+block.isBlockFacePowered(BlockFace.DOWN)).replace("%indirect%", ""+block.isBlockFaceIndirectlyPowered(BlockFace.DOWN)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "East'").replace("%direct%", ""+block.isBlockFacePowered(BlockFace.EAST)).replace("%indirect%", ""+block.isBlockFaceIndirectlyPowered(BlockFace.EAST)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "West'").replace("%direct%", ""+block.isBlockFacePowered(BlockFace.WEST)).replace("%indirect%", ""+block.isBlockFaceIndirectlyPowered(BlockFace.WEST)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "North'").replace("%direct%", ""+block.isBlockFacePowered(BlockFace.NORTH)).replace("%indirect%", ""+block.isBlockFaceIndirectlyPowered(BlockFace.NORTH)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "South'").replace("%direct%", ""+block.isBlockFacePowered(BlockFace.SOUTH)).replace("%indirect%", ""+block.isBlockFaceIndirectlyPowered(BlockFace.SOUTH)));
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.volt(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.data(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.brushMessage(Messages.VOLTMETER_BRUSH_MESSAGE);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.voltmeter";
    }
}
