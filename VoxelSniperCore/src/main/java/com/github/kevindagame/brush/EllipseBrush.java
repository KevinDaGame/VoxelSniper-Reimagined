package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#ellipse-brush">...</a>
 *
 * @author psanker
 */
public class EllipseBrush extends PerformerBrush {

    private static final double TWO_PI = (2 * Math.PI);
    private static final int SCL_MIN = 1;
    private static final int SCL_MAX = 9999;
    private static final int SCL_DEFAULT = 10;
    private static final int STEPS_MIN = 1;
    private static final int STEPS_MAX = 2000;
    private static final int STEPS_DEFAULT = 200;
    private int xscl;
    private int yscl;
    private int steps;
    private double stepSize;
    private boolean fill;


    private void ellipse(final SnipeData v, IBlock targetBlock) {
        try {
            for (double steps = 0; (steps <= TWO_PI); steps += stepSize) {
                final int x = (int) Math.round(this.xscl * Math.cos(steps));
                final int y = (int) Math.round(this.yscl * Math.sin(steps));

                switch (Objects.requireNonNull(getTargetBlock().getFace(this.getLastBlock()))) {
                    case NORTH:
                    case SOUTH:
                        positions.add(targetBlock.getRelative(0, x, y).getLocation());
                        break;
                    case EAST:
                    case WEST:
                        positions.add(targetBlock.getRelative(x, y, 0).getLocation());
                        break;
                    case UP:
                    case DOWN:
                        positions.add(targetBlock.getRelative(x, 0, y).getLocation());
                    default:
                        break;
                }

                if (steps >= TWO_PI) {
                    break;
                }
            }
        } catch (final Exception exception) {
            v.sendMessage(Messages.INVALID_TARGET);
        }
    }

    private void ellipsefill(final SnipeData v, IBlock targetBlock) {
        int ix = this.xscl;
        int iy = this.yscl;

        positions.add(targetBlock.getLocation());

        try {
            if (ix >= iy) { // Need this unless you want weird holes
                for (iy = this.yscl; iy > 0; iy--) {
                    for (double steps = 0; (steps <= TWO_PI); steps += stepSize) {
                        final int x = (int) Math.round(ix * Math.cos(steps));
                        final int y = (int) Math.round(iy * Math.sin(steps));

                        switch (Objects.requireNonNull(getTargetBlock().getFace(this.getLastBlock()))) {
                            case NORTH:
                            case SOUTH:
                                positions.add(targetBlock.getRelative(0, x, y).getLocation());
                                break;
                            case EAST:
                            case WEST:
                                positions.add(targetBlock.getRelative(x, y, 0).getLocation());
                                break;
                            case UP:
                            case DOWN:
                                positions.add(targetBlock.getRelative(x, 0, y).getLocation());
                            default:
                                break;
                        }

                        if (steps >= TWO_PI) {
                            break;
                        }
                    }
                    ix--;
                }
            } else {
                for (ix = this.xscl; ix > 0; ix--) {
                    for (double steps = 0; (steps <= TWO_PI); steps += stepSize) {
                        final int x = (int) Math.round(ix * Math.cos(steps));
                        final int y = (int) Math.round(iy * Math.sin(steps));

                        switch (Objects.requireNonNull(getTargetBlock().getFace(this.getLastBlock()))) {
                            case NORTH:
                            case SOUTH:
                                positions.add(targetBlock.getRelative(0, x, y).getLocation());
                                break;
                            case EAST:
                            case WEST:
                                positions.add(targetBlock.getRelative(x, y, 0).getLocation());
                                break;
                            case UP:
                            case DOWN:
                                positions.add(targetBlock.getRelative(x, 0, y).getLocation());
                            default:
                                break;
                        }

                        if (steps >= TWO_PI) {
                            break;
                        }
                    }
                    iy--;
                }
            }
        } catch (final Exception exception) {
            v.sendMessage(Messages.INVALID_TARGET);
        }
    }

    private void execute(final SnipeData v, IBlock targetBlock) {
        this.stepSize = (TWO_PI / this.steps);

        if (this.fill) {
            this.ellipsefill(v, targetBlock);
        } else {
            this.ellipse(v, targetBlock);
        }
    }

    @Override
    protected final void doArrow(final SnipeData v) {
        this.execute(v, this.getTargetBlock());
    }

    @Override
    protected final void doPowder(final SnipeData v) {
        this.execute(v, this.getLastBlock());
    }

    @Override
    public final void info(@NotNull final VoxelMessage vm) {
        if (this.xscl < SCL_MIN || this.xscl > SCL_MAX) {
            this.xscl = SCL_DEFAULT;
        }

        if (this.yscl < SCL_MIN || this.yscl > SCL_MAX) {
            this.yscl = SCL_DEFAULT;
        }

        if (this.steps < STEPS_MIN || this.steps > STEPS_MAX) {
            this.steps = STEPS_DEFAULT;
        }

        vm.brushName(this.getName());
        vm.custom(Messages.X_SIZE_SET.replace("%xscl%", String.valueOf(this.xscl)));
        vm.custom(Messages.Y_SIZE_SET.replace("%yscl%", String.valueOf(this.yscl)));
        vm.custom(Messages.ELLIPSE_RENDER_STEP_NUMBER.replace("%steps%", String.valueOf(this.steps)));
        vm.custom(Messages.ELLIPSEBRUSH_FILL_MODE.replace("%state%", this.fill ? "enabled" : "disabled"));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ELLIPSE_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("fill")) {
            this.fill = !this.fill;
            v.sendMessage(Messages.ELLIPSEBRUSH_FILL_MODE.replace("%state%", this.fill ? "enabled" : "disabled"));
            return;
        }

        try {
            if (params[0].startsWith("x")) {
                int xValue = Integer.parseInt(params[1]);

                if (xValue < SCL_MIN || xValue > SCL_MAX) {
                    v.sendMessage(Messages.INVALID_X_SCALE.replace("%SCL_MIN%", String.valueOf(SCL_MIN)).replace("%SCL_MAX%", String.valueOf(SCL_MAX)));
                    return;
                }

                this.xscl = xValue;
                v.sendMessage(Messages.X_SCALE_MODIFIER_SET.replace("%xscl%", String.valueOf(this.xscl)));
                return;
            }

            if (params[0].startsWith("y")) {
                int yValue = Integer.parseInt(params[1]);

                if (yValue < SCL_MIN || yValue > SCL_MAX) {
                    v.sendMessage(Messages.INVALID_Y_SCALE.replace("%SCL_MIN%", String.valueOf(SCL_MIN)).replace("%SCL_MAX%", String.valueOf(SCL_MAX)));
                    return;
                }

                this.yscl = yValue;
                v.sendMessage(Messages.Y_SCALE_MODIFIER_SET.replace("%yscl%", String.valueOf(this.yscl)));
                return;
            }

            if (params[0].startsWith("t")) {
                int stepValue = Integer.parseInt(params[1]);

                if (stepValue < STEPS_MIN || stepValue > STEPS_MAX) {
                    v.sendMessage(Messages.INVALID_STEP_AMOUNT.replace("%STEPS_MIN%", String.valueOf(STEPS_MIN)).replace("%STEPS_MAX%", String.valueOf(STEPS_MAX)));
                    return;
                }

                this.steps = stepValue;
                v.sendMessage(Messages.RENDER_STEP_NUMBER_SET.replace("%steps%", String.valueOf(this.steps)));
                return;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @NotNull
    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("x", "y", "t", "fill"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @NotNull
    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("x", Lists.newArrayList("[number]"));
        argumentValues.put("y", Lists.newArrayList("[number]"));
        argumentValues.put("t", Lists.newArrayList("[number]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }
}
