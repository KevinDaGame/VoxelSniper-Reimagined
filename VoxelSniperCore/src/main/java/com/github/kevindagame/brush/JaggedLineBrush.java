package com.github.kevindagame.brush;

import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.google.common.collect.Lists;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.*;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#jagged-line-brush">...</a>
 *
 * @author Giltwist
 * @author Monofraps
 */
public class JaggedLineBrush extends PerformerBrush {

    private static final VoxelVector HALF_BLOCK_OFFSET = new VoxelVector(0.5, 0.5, 0.5);
    private static final int timesUsed = 0;

    private static final int RECURSION_MIN = 1;
    private static final int RECURSION_DEFAULT = 3;
    private static final int RECURSION_MAX = 10;
    private static final int SPREAD_DEFAULT = 3;

    private final Random random = new Random();
    private VoxelVector originCoords = null;
    private VoxelVector targetCoords = new VoxelVector();
    private int recursion = RECURSION_DEFAULT;
    private int spread = SPREAD_DEFAULT;

    /**
     *
     */
    public JaggedLineBrush() {
        this.setName("Jagged Line");
    }

    private void jaggedP(final SnipeData v) {
        final VoxelVector originClone = this.originCoords.clone().add(JaggedLineBrush.HALF_BLOCK_OFFSET);
        final VoxelVector targetClone = this.targetCoords.clone().add(JaggedLineBrush.HALF_BLOCK_OFFSET);

        final VoxelVector direction = targetClone.clone().subtract(originClone);
        final double length = this.targetCoords.distance(this.originCoords);

        if (length == 0) {
            this.positions.add(this.targetCoords.getLocation(this.getWorld()));
        } else {
            for (final Iterator<IBlock> iterator = getWorld().getBlockIterator(originClone, direction, 0, ((int) Math.round(length))); iterator.hasNext(); ) {
                final IBlock block = iterator.next();
                for (int i = 0; i < recursion; i++) {
                    this.positions.add(new BaseLocation(getWorld(), Math.round(block.getX() + this.random.nextInt(spread * 2) - spread), Math.round(block.getY() + this.random.nextInt(spread * 2) - spread), Math.round(block.getZ() + this.random.nextInt(spread * 2) - spread)));
                }
            }
        }
    }

    @Override
    public final void doArrow(final SnipeData v) {
        if (originCoords == null) {
            originCoords = new VoxelVector();
        }
        this.originCoords = this.getTargetBlock().getLocation().toVector();
        v.sendMessage(Messages.FIRST_POINT_SELECTED);
    }

    @Override
    public final void doPowder(final SnipeData v) {
        if (originCoords == null) {
            v.sendMessage(Messages.FIRST_COORDINATE_NOT_SET);
        } else {
            this.targetCoords = this.getTargetBlock().getLocation().toVector();
            this.jaggedP(v);
        }

    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.JAGGED_LINE_BRUSH_RECURSION_SET.replace("%recursion%", String.valueOf(this.recursion)));
        vm.custom(Messages.JAGGED_LINE_BRUSH_SET_SPREAD.replace("%spread%", String.valueOf(this.spread)));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {

        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.JAGGED_LINE_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            if (params[0].equalsIgnoreCase("recursion")) {
                int newRecursion = Integer.parseInt(params[1]);
                if (newRecursion >= RECURSION_MIN && newRecursion <= RECURSION_MAX) {
                    this.recursion = newRecursion;
                    v.sendMessage(Messages.JAGGED_LINE_BRUSH_RECURSION_SET.replace("%recursion%", String.valueOf(this.recursion)));
                } else {
                    v.sendMessage(Messages.JAGGED_LINE_BRUSH_RECURSION_RANGE.replace("%RECURSION_MIN%", String.valueOf(RECURSION_MIN).replace("%RECURSION_MAX%", String.valueOf(RECURSION_MAX))));
                }

                return;
            }

            if (params[0].equalsIgnoreCase("spread")) {
                this.spread = Integer.parseInt(params[1]);
                v.sendMessage(Messages.JAGGED_LINE_BRUSH_SET_SPREAD.replace("%spread%", String.valueOf(this.spread)));
                return;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("recursion", "spread"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();

        argumentValues.put("recursion", Lists.newArrayList("[number]"));
        argumentValues.put("spread", Lists.newArrayList("[number]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.jaggedline";
    }
}
