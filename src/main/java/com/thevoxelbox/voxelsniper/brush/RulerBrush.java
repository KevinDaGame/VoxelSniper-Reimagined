package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VoxelVector;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Ruler_Brush
 *
 * @author Gavjenks
 */
public class RulerBrush extends Brush {

    private boolean first = true;
    private VoxelVector coords = new VoxelVector();

    private final int xOff = 0;
    private final int yOff = 0;
    private final int zOff = 0;

    /**
     *
     */
    public RulerBrush() {
        this.setName("Ruler");
    }

    @Override
    protected final void arrow(final SnipeData v) {
        final VoxelMaterial voxelMaterial = v.getVoxelMaterial();
        this.coords = this.getTargetBlock().getLocation().toVector();

        if (this.xOff == 0 && this.yOff == 0 && this.zOff == 0) {
            v.sendMessage(Messages.FIRST_POINT_SELECTED);
            this.first = !this.first;
        } else {
            final Undo undo = new Undo();
            setBlockMaterialAt(this.getTargetBlock().getX() + this.xOff, this.getTargetBlock().getY() + this.yOff, this.getTargetBlock().getZ() + this.zOff, voxelMaterial, undo);
            v.owner().storeUndo(undo);
        }
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.coords == null || this.coords.lengthSquared() == 0) {
            v.sendMessage(Messages.FIRST_COORDINATE_NOT_SET);
            return;
        }

        double x = this.getTargetBlock().getX() - this.coords.getX();
        double y = this.getTargetBlock().getY() - this.coords.getY();
        double z = this.getTargetBlock().getZ() - this.coords.getZ();
        final double distance = (double) (Math.round(this.getTargetBlock().getLocation().toVector().subtract(this.coords).length() * 100) / 100);
        final double blockDistance = (double) (Math.round((Math.abs(Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z))) + 1) * 100) / 100);

        v.sendMessage(Messages.RULER_BRUSH_POWDER.replace("%x%",String.valueOf(x)).replace("%y%",String.valueOf(y)).replace("%z%",String.valueOf(z)).replace("%distance%",String.valueOf(distance)).replace("%blockDistance%",String.valueOf(blockDistance)));
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.voxel();
    }

    @Override
    // TODO: Implement block placing
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.RULER_BRUSH_USAGE);
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.ruler";
    }
}
