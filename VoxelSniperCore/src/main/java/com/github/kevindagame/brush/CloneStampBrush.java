package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.BlockWrapper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.RotationAxis;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The CloneStamp class is used to create a collection of blocks in a cylinder shape according to the selection the player has set.
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#clone-brush">...</a>
 *
 * @author Voxel
 */
public class CloneStampBrush extends StampBrush {

    private BaseLocation startingPoint;


    /**
     * The clone method is used to grab a snapshot of the selected area dictated blockPositionY targetBlock.x y z v.brushSize v.voxelHeight and v.cCen.
     * <p/>
     * x y z -- initial center of the selection v.brushSize -- the radius of the cylinder v.voxelHeight -- the heigth of the cylinder c.cCen -- the offset on
     * the Y axis of the selection ( bottom of the cylinder ) as blockPositionY: Bottom_Y = targetBlock.y + v.cCen;
     *
     * @param v the caller
     */
    private void clone(final SnipeData v) {
        VoxelLocation point = getTargetBlock().getLocation().makeMutable();
        point.add(0, v.getcCen(), 0);
        this.startingPoint = point.makeImmutable();
        var positions = Shapes.cylinder(startingPoint, RotationAxis.Y, v.getBrushSize(), v.getVoxelHeight(), 0, false);
        this.clone.clear();
        this.toStamp.clear();
        this.sorted = false;
        for (var p : positions) {
            this.clone.add(new BlockWrapper(p.getBlock(), p.getBlockX() - startingPoint.getBlockX(), p.getBlockY() - startingPoint.getBlockY(), p.getBlockZ() - startingPoint.getBlockZ(), getWorld()));
        }
        v.sendMessage(Messages.BLOCKS_COPIED_SUCCESSFULLY.replace("%amount%", String.valueOf(this.clone.size())));

    }

    @Override
    protected final void powder(final SnipeData v) {
        this.clone(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.height();
        vm.center();
        switch (this.stamp) {
            case DEFAULT:
                vm.brushMessage(Messages.DEFAULT_STAMP);
                break;

            case NO_AIR:
                vm.brushMessage(Messages.NO_AIR_STAMP);
                break;

            case FILL:
                vm.brushMessage(Messages.FILL_STAMP);
                break;

            default:
                vm.custom(Messages.STAMP_ERROR);
                break;
        }
    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.CLONE_STAMP_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("air")) {
            this.setStamp(StampType.NO_AIR);
            this.reSort();
            v.sendMessage(Messages.CLONE_STAMP_NO_AIR);
            return;
        }

        if (params[0].equalsIgnoreCase("fill")) {
            this.setStamp(StampType.FILL);
            this.reSort();
            v.sendMessage(Messages.CLONE_STAMP_FILL);
            return;
        }

        if (params[0].equalsIgnoreCase("default")) {
            this.setStamp(StampType.DEFAULT);
            this.reSort();
            v.sendMessage(Messages.CLONE_STAMP_DEFAULT_MODE);
            return;
        }

        // TODO: Implement if (params[0].startsWith("centre")) { v.setcCen(Integer.parseInt(params[0].replace("c", ""))); v.sendMessage(ChatColor.BLUE + "Center
        // set to " + v.getcCen()); return; }
        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("air", "fill", "default"));
    }
}
