package com.github.kevindagame.brush.multiBlock;

import com.github.kevindagame.brush.AbstractBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Utils;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.operation.BlockOperation;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#copypasta-brush">...</a>
 *
 * @author giltwist
 */
public class CopyPastaBrush extends AbstractBrush {

    private static final int BLOCK_LIMIT = 10000;
    private final int[] pastePoint = new int[3];
    private final int[] minPoint = new int[3];
    private final int[] offsetPoint = new int[3];
    private final int[] arraySize = new int[3];
    private boolean pasteAir = true; // False = no air, true = air
    private int points = 0; //
    private int numBlocks = 0;
    private int[] firstPoint = new int[3];
    private int[] secondPoint = new int[3];
    private IBlockData[] substanceArray;
    private int pivot = 0; // ccw degrees    


    private void doCopy(final SnipeData v) {
        for (int i = 0; i < 3; i++) {
            this.arraySize[i] = Math.abs(this.firstPoint[i] - this.secondPoint[i]) + 1;
            this.minPoint[i] = Math.min(this.firstPoint[i], this.secondPoint[i]);
            this.offsetPoint[i] = this.minPoint[i] - this.firstPoint[i]; // will always be negative or zero
        }

        this.numBlocks = (this.arraySize[0]) * (this.arraySize[1]) * (this.arraySize[2]);

        if (this.numBlocks > 0 && this.numBlocks < CopyPastaBrush.BLOCK_LIMIT) {
            this.substanceArray = new IBlockData[this.numBlocks];

            for (int i = 0; i < this.arraySize[0]; i++) {
                for (int j = 0; j < this.arraySize[1]; j++) {
                    for (int k = 0; k < this.arraySize[2]; k++) {
                        final int currentPosition = i + this.arraySize[0] * j + this.arraySize[0] * this.arraySize[1] * k;
                        this.substanceArray[currentPosition] = this.getWorld().getBlock(this.minPoint[0] + i, this.minPoint[1] + j, this.minPoint[2] + k).getBlockData();
                    }
                }
            }

            v.sendMessage(Messages.BLOCKS_COPIED_COUNT.replace("%numBlocks%", String.valueOf(this.numBlocks)));
        } else {
            v.sendMessage(Messages.COPY_AREA_TOO_BIG.replace("%numBlocks%", String.valueOf(this.numBlocks)).replace("%BLOCK_LIMIT%", String.valueOf(CopyPastaBrush.BLOCK_LIMIT)));
        }
    }

    private void doPasta(final SnipeData v) {
        final Undo undo = new Undo();

        for (int i = 0; i < this.arraySize[0]; i++) {
            for (int j = 0; j < this.arraySize[1]; j++) {
                for (int k = 0; k < this.arraySize[2]; k++) {
                    final int currentPosition = i + this.arraySize[0] * j + this.arraySize[0] * this.arraySize[1] * k;
                    IBlock block;

                    switch (this.pivot) {
                        case 180:
                            block = this.getWorld().getBlock(this.pastePoint[0] - this.offsetPoint[0] - i, this.pastePoint[1] + this.offsetPoint[1] + j, this.pastePoint[2] - this.offsetPoint[2] - k);
                            break;
                        case 270:
                            block = this.getWorld().getBlock(this.pastePoint[0] + this.offsetPoint[2] + k, this.pastePoint[1] + this.offsetPoint[1] + j, this.pastePoint[2] - this.offsetPoint[0] - i);
                            break;
                        case 90:
                            block = this.getWorld().getBlock(this.pastePoint[0] - this.offsetPoint[2] - k, this.pastePoint[1] + this.offsetPoint[1] + j, this.pastePoint[2] + this.offsetPoint[0] + i);
                            break;
                        default: // assume no rotation
                            block = this.getWorld().getBlock(this.pastePoint[0] + this.offsetPoint[0] + i, this.pastePoint[1] + this.offsetPoint[1] + j, this.pastePoint[2] + this.offsetPoint[2] + k);
                            break;
                    }

                    if (!(!this.pasteAir && this.substanceArray[currentPosition].getMaterial().isAir())) {
                        addOperation(new BlockOperation(block.getLocation(), block.getBlockData(), this.substanceArray[currentPosition]));
                    }
                }
            }
        }
        v.sendMessage(Messages.BLOCKS_PASTED_COUNT.replace("%numBlocks%", String.valueOf(this.numBlocks)));

        v.owner().storeUndo(undo);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        switch (this.points) {
            case 0:
                this.firstPoint[0] = this.getTargetBlock().getX();
                this.firstPoint[1] = this.getTargetBlock().getY();
                this.firstPoint[2] = this.getTargetBlock().getZ();
                v.sendMessage(Messages.FIRST_POINT_SELECTED);
                this.points = 1;
                break;
            case 1:
                this.secondPoint[0] = this.getTargetBlock().getX();
                this.secondPoint[1] = this.getTargetBlock().getY();
                this.secondPoint[2] = this.getTargetBlock().getZ();
                v.sendMessage(Messages.SECOND_POINT_SELECTED);
                this.points = 2;
                break;
            default:
                this.firstPoint = new int[3];
                this.secondPoint = new int[3];
                this.numBlocks = 0;
                this.substanceArray = new IBlockData[1];
                this.points = 0;
                v.sendMessage(Messages.POINTS_CLEARED);
                break;
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.points == 2) {
            if (this.numBlocks == 0) {
                this.doCopy(v);
            } else if (this.numBlocks > 0 && this.numBlocks < CopyPastaBrush.BLOCK_LIMIT) {
                this.pastePoint[0] = this.getTargetBlock().getX();
                this.pastePoint[1] = this.getTargetBlock().getY();
                this.pastePoint[2] = this.getTargetBlock().getZ();
                this.doPasta(v);
            } else {
                v.sendMessage(Messages.ERROR);
            }
        } else {
            v.sendMessage(Messages.MUST_SELECT_2_POINTS);
        }
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.PASTE_AIR_STATE.replace("%pasteAir%", String.valueOf(this.pasteAir)));
        vm.custom(Messages.PIVOT_ANGLE.replace("%pivot%", String.valueOf(this.pivot)));
    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.COPYPASTA_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("air")) {
            this.pasteAir = !this.pasteAir;

            v.sendMessage(Messages.PASTE_AIR_STATE.replace("%pasteAir%", String.valueOf(this.pasteAir)));
            return;
        }

        if (params[0].equalsIgnoreCase("rotate")) {
            if (params[1].equalsIgnoreCase("90") || params[1].equalsIgnoreCase("180") || params[1].equalsIgnoreCase("270") || params[1].equalsIgnoreCase("0")) {
                this.pivot = Integer.parseInt(params[1]);
                v.sendMessage(Messages.PIVOT_ANGLE.replace("%pivot%", String.valueOf(this.pivot)));
                return;
            }
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Utils.newArrayList("rotate", "air"));
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();


        argumentValues.put("rotate", Utils.newArrayList("0", "90", "180", "270"));

        return argumentValues;
    }
}
