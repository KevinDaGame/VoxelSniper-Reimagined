package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.CustomOperation;
import com.github.kevindagame.util.brushOperation.CustomOperationContext;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.blockdata.redstoneWire.IRedstoneWire;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#voltmeter-brush">...</a>
 *
 * @author Gavjenks
 */
public class VoltMeterBrush extends CustomBrush {


    private void data(final SnipeData v) {
        final IBlockData data = getTargetBlock().getBlockData();
        if (data instanceof IRedstoneWire redstone) {
            v.sendMessage(Messages.REDSTONE_POWER_LEVEL.replace("%blocks%", String.valueOf(redstone.getPower())));
        }
    }

    private void volt(final SnipeData v) {
        var block = getTargetBlock();
        final boolean indirect = block.isBlockIndirectlyPowered();
        final boolean direct = block.isBlockPowered();
        v.sendMessage(Messages.BLOCK_POWER_MESSAGE.replace("%direct%", String.valueOf(direct)).replace("%indirect%", String.valueOf(indirect)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "Top").replace("%direct%", "" + block.isBlockFacePowered(BlockFace.UP)).replace("%indirect%", "" + block.isBlockFaceIndirectlyPowered(BlockFace.UP)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "Bottom").replace("%direct%", "" + block.isBlockFacePowered(BlockFace.DOWN)).replace("%indirect%", "" + block.isBlockFaceIndirectlyPowered(BlockFace.DOWN)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "East").replace("%direct%", "" + block.isBlockFacePowered(BlockFace.EAST)).replace("%indirect%", "" + block.isBlockFaceIndirectlyPowered(BlockFace.EAST)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "West").replace("%direct%", "" + block.isBlockFacePowered(BlockFace.WEST)).replace("%indirect%", "" + block.isBlockFaceIndirectlyPowered(BlockFace.WEST)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "North").replace("%direct%", "" + block.isBlockFacePowered(BlockFace.NORTH)).replace("%indirect%", "" + block.isBlockFaceIndirectlyPowered(BlockFace.NORTH)));
        v.sendMessage(Messages.FACE_POWER_MESSAGE.replace("%face%", "South").replace("%direct%", "" + block.isBlockFacePowered(BlockFace.SOUTH)).replace("%indirect%", "" + block.isBlockFaceIndirectlyPowered(BlockFace.SOUTH)));
    }

    @Override
    protected final void arrow(final SnipeData v) {
        addOperation(new CustomOperation(this.getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    protected final void powder(final SnipeData v) {
        addOperation(new CustomOperation(this.getTargetBlock().getLocation(), this, v, CustomOperationContext.TARGETLOCATION));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.brushMessage(Messages.VOLTMETER_BRUSH_MESSAGE);
    }

    @Override
    public boolean perform(@NotNull List<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        switch (Objects.requireNonNull(this.getSnipeAction())) {
            case ARROW:
                volt(snipeData);
                return true;
            case GUNPOWDER:
                data(snipeData);
                return true;
            default:
                return false;
        }
    }
}
