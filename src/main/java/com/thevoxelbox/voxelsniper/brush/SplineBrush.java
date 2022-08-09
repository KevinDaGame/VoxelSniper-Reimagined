package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * FOR ANY BRUSH THAT USES A SPLINE, EXTEND THAT BRUSH FROM THIS BRUSH!!! That way, the spline calculations are already there. Also, the UI for the splines will
 * be included.
 *
 * @author psanker
 */
public class SplineBrush extends PerformerBrush {

    private final ArrayList<IBlock> endPts = new ArrayList<>();
    private final ArrayList<IBlock> ctrlPts = new ArrayList<>();
    protected ArrayList<Point> spline = new ArrayList<>();
    protected boolean set;
    protected boolean ctrl;
    protected String[] sparams = {"ss", "sc", "clear"};

    public SplineBrush() {
        this.setName("Spline");
    }

    public final void addToSet(final SnipeData v, final boolean ep, IBlock targetBlock) {
        String pos = "(" + targetBlock.getX() + ", " + targetBlock.getY() + ", " + targetBlock.getZ() + ") ";
        if (ep) {
            if (this.endPts.contains(targetBlock) || this.endPts.size() == 2) {
                return;
            }

            this.endPts.add(targetBlock);
            v.sendMessage(Messages.ADDED_BLOCK_ENDPOINT.replace("%pos%",pos));
            return;
        }

        if (this.ctrlPts.contains(targetBlock) || this.ctrlPts.size() == 2) {
            return;
        }

        this.ctrlPts.add(targetBlock);
        v.sendMessage(Messages.ADDED_BLOCK_CONTROL.replace("%pos%",pos));
    }

    public final void removeFromSet(final SnipeData v, final boolean ep,  IBlock targetBlock) {
        String pos = "(" + targetBlock.getX() + ", " + targetBlock.getY() + ", " + targetBlock.getZ() + ") ";
        if (ep) {
            if (!this.endPts.contains(targetBlock)) {
                v.sendMessage(Messages.BLOCK_NOT_IN_ENDPOINT_SELECTION);
                return;
            }

            this.endPts.add(targetBlock);
            v.sendMessage(Messages.REMOVED_BLOCK_ENDPOINT.replace("%pos%",pos));
            return;
        }

        if (!this.ctrlPts.contains(targetBlock)) {
            v.sendMessage(Messages.BLOCK_NOT_IN_CONTROL_POINT_SELECTION);
            return;
        }

        this.ctrlPts.remove(targetBlock);
        v.sendMessage(Messages.REMOVED_BLOCK_CONTROL.replace("%pos%",pos));
    }

    public final boolean spline(final Point start, final Point end, final Point c1, final Point c2, final SnipeData v) {
        this.spline.clear();

        try {
            final Point c = (c1.subtract(start)).multiply(3);
            final Point b = ((c2.subtract(c1)).multiply(3)).subtract(c);
            final Point a = ((end.subtract(start)).subtract(c)).subtract(b);

            for (double t = 0.0; t < 1.0; t += 0.01) {
                final int px = (int) Math.round((a.getX() * (t * t * t)) + (b.getX() * (t * t)) + (c.getX() * t) + this.endPts.get(0).getX());
                final int py = (int) Math.round((a.getY() * (t * t * t)) + (b.getY() * (t * t)) + (c.getY() * t) + this.endPts.get(0).getY());
                final int pz = (int) Math.round((a.getZ() * (t * t * t)) + (b.getZ() * (t * t)) + (c.getZ() * t) + this.endPts.get(0).getZ());

                if (!this.spline.contains(new Point(px, py, pz))) {
                    this.spline.add(new Point(px, py, pz));
                }
            }

            return true;
        } catch (final Exception exception) {
            v.sendMessage(Messages.SPLINE_BRUSH_NOT_ENOUGH_POINTS.replace("%endPts%", String.valueOf(this.endPts.size())).replace("%ctrlPts%", String.valueOf(this.ctrlPts.size())));
            return false;
        }
    }

    protected final void render(final SnipeData v) {
        if (this.spline.isEmpty()) {
            return;
        }

        for (final Point point : this.spline) {
            this.currentPerformer.perform(this.clampY(point.getX(), point.getY(), point.getZ()));
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (this.set) {
            this.removeFromSet(v, true, this.getTargetBlock());
        } else if (this.ctrl) {
            this.removeFromSet(v, false, this.getTargetBlock());
        }
    }

    protected final void clear(final SnipeData v) {
        this.spline.clear();
        this.ctrlPts.clear();
        this.endPts.clear();
        v.sendMessage(Messages.BEZIER_CURVE_CLEARED);
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (this.set) {
            this.addToSet(v, true, this.getTargetBlock());
        }
        if (this.ctrl) {
            this.addToSet(v, false, this.getTargetBlock());
        }
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());

        if (this.set) {
            vm.custom(Messages.ENDPOINT_SELECTION_MODE_ENABLED);
        } else if (this.ctrl) {
            vm.custom(Messages.CONTROL_POINT_SELECTION_MODE_ENABLED);
        } else {
            vm.custom(Messages.NO_SELECTION_MODE);
        }
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SPLINE_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("sc")) {
            if (!this.ctrl) {
                this.set = false;
                this.ctrl = true;
                v.sendMessage(Messages.CONTROL_POINT_SELECTION_MODE_ENABLED);
            } else {
                this.ctrl = false;
                v.sendMessage(Messages.CONTROL_POINT_SELECTION_MODE_DISABLED);
            }
            return;
        }

        if (params[0].equalsIgnoreCase("ss")) {
            if (!this.set) {
                this.set = true;
                this.ctrl = false;
                v.sendMessage(Messages.ENDPOINT_SELECTION_MODE_ENABLED);
            } else {
                this.set = false;
                v.sendMessage(Messages.ENDPOINT_SELECTION_MODE_DISABLED);
            }
            return;
        }

        if (params[0].equalsIgnoreCase("clear")) {
            this.clear(v);
            return;
        }

        if (params[0].equalsIgnoreCase("render")) {
            if (this.spline(new Point(this.endPts.get(0)), new Point(this.endPts.get(1)), new Point(this.ctrlPts.get(0)), new Point(this.ctrlPts.get(1)), v)) {
                this.render(v);
            }
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("clear", "sc", "ss", "render"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    // Vector class for splines
    protected class Point {

        private int x;
        private int y;
        private int z;

        public Point(final  IBlock  b) {
            this.setX(b.getX());
            this.setY(b.getY());
            this.setZ(b.getZ());
        }

        public Point(final int x, final int y, final int z) {
            this.setX(x);
            this.setY(y);
            this.setZ(z);
        }

        public final Point add(final Point p) {
            return new Point(this.getX() + p.getX(), this.getY() + p.getY(), this.getZ() + p.getZ());
        }

        public final Point multiply(final int scalar) {
            return new Point(this.getX() * scalar, this.getY() * scalar, this.getZ() * scalar);
        }

        public final Point subtract(final Point p) {
            return new Point(this.getX() - p.getX(), this.getY() - p.getY(), this.getZ() - p.getZ());
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.spline";
    }
}
