package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.HashSet;
import java.util.Set;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Dome_Brush
 *
 * @author Gavjenks
 * @author MikeMatrix
 */
public class DomeBrush extends AbstractBrush {

    /**
     *
     */
    public DomeBrush() {
        this.setName("Dome");
    }

    /**
     *
     * @param v
     */
    private void generateDome(final SnipeData v) {

        if (v.getVoxelHeight() == 0) {
            v.sendMessage(Messages.VOXEL_HEIGHT_MUST_NOT_BE_0);
            this.cancel();
            return;
        }

        this.positions = Shapes.dome(this.getTargetBlock().getLocation(), v.getBrushSize(), v.getVoxelHeight());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        vm.height();
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.generateDome(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.generateDome(v);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.dome";
    }
}
