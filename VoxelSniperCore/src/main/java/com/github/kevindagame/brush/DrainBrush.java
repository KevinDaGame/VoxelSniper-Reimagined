package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#drain-brush">...</a>
 *
 * @author Gavjenks
 * @author psanker
 */
public class DrainBrush extends AbstractBrush {

    private final double trueCircle = 0.5;
    private boolean disc = false;

    /**
     *
     */
    public DrainBrush() {
        this.setName("Drain");
    }

    @SuppressWarnings("deprecation")
    private void drain(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + this.trueCircle, 2);
        final Undo undo = new Undo();
        if (this.disc) {
            for (int x = brushSize; x >= 0; x--) {
                final double xSquared = Math.pow(x, 2);

                for (int z = brushSize; z >= 0; z--) {
                    if ((xSquared + Math.pow(z, 2)) <= brushSizeSquared) {
                        for (int dx : new int[]{-1, 1}) {
                            for (int dz : new int[]{-1, 1}) {
                                IBlock b = this.clampY(this.getTargetBlock().getX() + (x * dx), this.getTargetBlock().getY(), this.getTargetBlock().getZ() + (z * dz));
                                if (b.getMaterial().isFluid()) {
                                    undo.put(b);
                                    b.setMaterial(VoxelMaterial.AIR);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (int x = (brushSize + 1) * 2; x >= 0; x--) {
                final double xSquared = Math.pow(x - brushSize, 2);

                for (int z = (brushSize + 1) * 2; z >= 0; z--) {
                    final double zSquared = Math.pow(z - brushSize, 2);

                    for (int y = (brushSize + 1) * 2; y >= 0; y--) {
                        if ((xSquared + Math.pow(y - brushSize, 2) + zSquared) <= brushSizeSquared) {
                            IBlock b = this.clampY(this.getTargetBlock().getX() + x - brushSize, this.getTargetBlock().getY() + y - brushSize, this.getTargetBlock().getZ() + z - brushSize);
                            if (b.getMaterial().isFluid()) {
                                undo.put(b);
                                b.setMaterial(VoxelMaterial.AIR);
                            }
                        }
                    }
                }
            }
        }

        v.owner().storeUndo(undo);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.drain(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.drain(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();

        vm.custom(Messages.DRAIN_TRUE_CIRCLE_MODE.replace("%state%", (this.trueCircle == 0.5) ? "ON" : "OFF"));
        vm.custom(Messages.DISC_DRAIN_MODE.replace("%state%", (this.disc) ? "ON" : "OFF"));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.DRAIN_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }
        if (params[0].startsWith("shape")) {
            this.disc = !this.disc;
            v.sendMessage(Messages.DRAIN_BRUSH_SHAPE.replace("%shape%", this.disc ? "Disc" : "Ball"));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("shape"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.drain";
    }
}
