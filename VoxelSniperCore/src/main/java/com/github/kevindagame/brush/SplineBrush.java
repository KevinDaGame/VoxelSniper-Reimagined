package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * FOR ANY BRUSH THAT USES A SPLINE, EXTEND THAT BRUSH FROM THIS BRUSH!!! That way, the spline calculations are already there. Also, the UI for the splines will
 * be included.
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#spline-brush">...</a>
 *
 * @author psanker
 */
public class SplineBrush extends PerformerBrush {

    private final ArrayList<Point> endPts = new ArrayList<>();
    private final ArrayList<Point> ctrlPts = new ArrayList<>();
    protected boolean set;
    protected boolean ctrl;

    public SplineBrush() {
        this.setName("Spline");
    }

    public final void addToSet(final SnipeData v, final boolean ep, IBlock targetBlock) {
        String pos = "(" + targetBlock.getX() + ", " + targetBlock.getY() + ", " + targetBlock.getZ() + ") ";
        Point point = new Point(targetBlock);
        if (ep) {
            if (this.endPts.contains(point)) {
                v.sendMessage(Messages.BLOCK_ALREADY_ADDED_ENDPOINT.replace("%pos%", pos));
                return;
            }
            if (this.endPts.size() == 2) {
                v.sendMessage(Messages.ENDPOINT_SELECTION_FULL);
                return;
            }

            this.endPts.add(point);
            v.sendMessage(Messages.ADDED_BLOCK_ENDPOINT.replace("%pos%", pos));
            return;
        }

        if (this.ctrlPts.contains(point)) {
            v.sendMessage(Messages.BLOCK_ALREADY_ADDED_CONTROL.replace("%pos%", pos));
            return;
        }
        if (this.ctrlPts.size() == 2) {
            v.sendMessage(Messages.CONTROL_SELECTION_FULL);
            return;
        }

        this.ctrlPts.add(point);
        v.sendMessage(Messages.ADDED_BLOCK_CONTROL.replace("%pos%", pos));
    }

    public final void removeFromSet(final SnipeData v, final boolean ep, IBlock targetBlock) {
        String pos = "(" + targetBlock.getX() + ", " + targetBlock.getY() + ", " + targetBlock.getZ() + ") ";
        Point point = new Point(targetBlock);
        if (ep) {
            if (!this.endPts.contains(point)) {
                v.sendMessage(Messages.BLOCK_NOT_IN_ENDPOINT_SELECTION);
                return;
            }

            this.endPts.add(point);
            v.sendMessage(Messages.REMOVED_BLOCK_ENDPOINT.replace("%pos%", pos));
            return;
        }

        if (!this.ctrlPts.contains(point)) {
            v.sendMessage(Messages.BLOCK_NOT_IN_CONTROL_POINT_SELECTION);
            return;
        }

        this.ctrlPts.remove(point);
        v.sendMessage(Messages.REMOVED_BLOCK_CONTROL.replace("%pos%", pos));
    }

    public final boolean spline(final Point start, final Point end, final Point c1, final Point c2, final SnipeData v) {
        this.positions.clear();

        try {
            final Point c = (c1.subtract(start)).multiply(3);
            final Point b = ((c2.subtract(c1)).multiply(3)).subtract(c);
            final Point a = ((end.subtract(start)).subtract(c)).subtract(b);

            for (double t = 0.0; t < 1.0; t += 0.01) {
                final int px = (int) Math.round((a.getX() * (t * t * t)) + (b.getX() * (t * t)) + (c.getX() * t) + start.getX());
                final int py = (int) Math.round((a.getY() * (t * t * t)) + (b.getY() * (t * t)) + (c.getY() * t) + start.getY());
                final int pz = (int) Math.round((a.getZ() * (t * t * t)) + (b.getZ() * (t * t)) + (c.getZ() * t) + start.getZ());
                positions.add(new BaseLocation(getWorld(), px, py, pz));
            }
            //manually add operations because the performer logic is not called here
            addOperations(currentPerformer.perform(positions));
            return true;
        } catch (final Exception exception) {
            v.sendMessage(Messages.SPLINE_BRUSH_NOT_ENOUGH_POINTS.replace("%endPts%", String.valueOf(this.endPts.size())).replace("%ctrlPts%", String.valueOf(this.ctrlPts.size())));
            return false;
        }
    }

    @Override
    protected final void doArrow(final SnipeData v) {
        if (this.set) {
            this.removeFromSet(v, true, this.getTargetBlock());
        } else if (this.ctrl) {
            this.removeFromSet(v, false, this.getTargetBlock());
        }
        this.positions.clear();
    }

    protected final void clear(final SnipeData v) {
        this.positions.clear();
        this.ctrlPts.clear();
        this.endPts.clear();
        v.sendMessage(Messages.BEZIER_CURVE_CLEARED);
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        if (this.set) {
            this.addToSet(v, true, this.getTargetBlock());
        }
        if (this.ctrl) {
            this.addToSet(v, false, this.getTargetBlock());
        }
        this.positions.clear();
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
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.SPLINE_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
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
            if (this.endPts.size() < 2 || this.ctrlPts.size() < 2) {
                v.sendMessage(Messages.SPLINE_BRUSH_NOT_ENOUGH_POINTS.replace("%endPts%", String.valueOf(this.endPts.size())).replace("%ctrlPts%", String.valueOf(this.ctrlPts.size())));
                return;
            }
            if (this.spline(this.endPts.get(0), this.endPts.get(1), this.ctrlPts.get(0), this.ctrlPts.get(1), v)) {
                this.initP(v);
                this.performOperations(v);
            }
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @NotNull
    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("clear", "sc", "ss", "render"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.spline";
    }

    // Vector class for splines
    private static class Point {

        private int x;
        private int y;
        private int z;

        public Point(final IBlock b) {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Point point) return x == point.x && y == point.y && z == point.z;
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
