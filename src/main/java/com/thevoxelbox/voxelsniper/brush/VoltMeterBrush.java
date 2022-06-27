package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import org.bukkit.ChatColor;
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
        final IBlock block = this.clampY(this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ());
        final byte data = block.getData();
        v.sendMessage(ChatColor.AQUA + "Blocks until repeater needed: " + data);
    }

    private void volt(final SnipeData v) {
        final  IBlock  block = this.clampY(this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ());
        final boolean indirect = block.isBlockIndirectlyPowered();
        final boolean direct = block.isBlockPowered();
        v.sendMessage(ChatColor.AQUA + "Direct Power? " + direct + " Indirect Power? " + indirect);
        v.sendMessage(ChatColor.BLUE + "Top Direct? " + block.isBlockFacePowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.UP.toString())) + " Top Indirect? " + block.isBlockFaceIndirectlyPowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.UP.toString())));
        v.sendMessage(ChatColor.BLUE + "Bottom Direct? " + block.isBlockFacePowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.DOWN.toString())) + " Bottom Indirect? " + block.isBlockFaceIndirectlyPowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.DOWN.toString())));
        v.sendMessage(ChatColor.BLUE + "East Direct? " + block.isBlockFacePowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.EAST.toString())) + " East Indirect? " + block.isBlockFaceIndirectlyPowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.EAST.toString())));
        v.sendMessage(ChatColor.BLUE + "West Direct? " + block.isBlockFacePowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.WEST.toString())) + " West Indirect? " + block.isBlockFaceIndirectlyPowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.WEST.toString())));
        v.sendMessage(ChatColor.BLUE + "North Direct? " + block.isBlockFacePowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.NORTH.toString())) + " North Indirect? " + block.isBlockFaceIndirectlyPowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.NORTH.toString())));
        v.sendMessage(ChatColor.BLUE + "South Direct? " + block.isBlockFacePowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.SOUTH.toString())) + " South Indirect? " + block.isBlockFaceIndirectlyPowered(com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace.valueOf(BlockFace.SOUTH.toString())));
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
        vm.brushMessage("Right click with arrow to see if blocks/faces are powered. Powder measures wire current.");
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.voltmeter";
    }
}
