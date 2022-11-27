package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeAction;
import com.github.kevindagame.util.BlockWrapper;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * The CloneStamp class is used to create a collection of blocks in a cylinder shape according to the selection the player has set.
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Clone_and_CopyPasta_Brushes
 *
 * @author Voxel
 */
public class CloneStampBrush extends StampBrush {

    private VoxelLocation startingPoint;

    /**
     *
     */
    public CloneStampBrush() {
        this.setName("Clone");
    }

    /**
     * The clone method is used to grab a snapshot of the selected area dictated blockPositionY targetBlock.x y z v.brushSize v.voxelHeight and v.cCen.
     * <p/>
     * x y z -- initial center of the selection v.brushSize -- the radius of the cylinder v.voxelHeight -- the heigth of the cylinder c.cCen -- the offset on
     * the Y axis of the selection ( bottom of the cylinder ) as blockPositionY: Bottom_Y = targetBlock.y + v.cCen;
     *
     * @param v the caller
     */
    private void clone(final SnipeData v) {
        this.startingPoint = getTargetBlock().getLocation().clone();
        this.startingPoint.add(0, v.getcCen(), 0);
        this.positions = Shapes.cylinder(startingPoint, v.getBrushSize(), v.getVoxelHeight(), 0, false);

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
    protected boolean actPerform(SnipeData v) {
        if(super.actPerform(v)) {
            return true;
        }
        if(snipeAction == SnipeAction.GUNPOWDER) {
            this.clone.clear();
            this.toStamp.clear();
            this.sorted = false;
            for(var p: positions) {
                this.clone.add(new BlockWrapper(p.getBlock(), p.getBlockX() - startingPoint.getBlockX(), p.getBlockY() - startingPoint.getBlockY(), p.getBlockZ() - startingPoint.getBlockZ(), getWorld()));
            }
            v.sendMessage(Messages.BLOCKS_COPIED_SUCCESSFULLY.replace("%amount%", String.valueOf(this.clone.size())));
            return true;
        }
        return false;
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
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

        /**
         * TODO: Implement if (params[0].startsWith("centre")) { v.setcCen(Integer.parseInt(params[0].replace("c", ""))); v.sendMessage(ChatColor.BLUE + "Center
         * set to " + v.getcCen()); return; }
         */
        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("air", "fill", "default"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.clonestamp";
    }
}
