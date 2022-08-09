package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.BukkitVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VectorFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.NumberConversions;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Jagged_Line_Brush
 *
 * @author Giltwist
 * @author Monofraps
 */
public class JaggedLineBrush extends PerformerBrush {

    private static final IVector HALF_BLOCK_OFFSET = VectorFactory.getVector(0.5, 0.5, 0.5);
    private static final int timesUsed = 0;

    private static final int RECURSION_MIN = 1;
    private static final int RECURSION_DEFAULT = 3;
    private static final int RECURSION_MAX = 10;
    private static final int SPREAD_DEFAULT = 3;

    private final Random random = new Random();
    private IVector originCoords = null;
    private IVector targetCoords = VectorFactory.getVector();
    private int recursion = RECURSION_DEFAULT;
    private int spread = SPREAD_DEFAULT;

    /**
     *
     */
    public JaggedLineBrush() {
        this.setName("Jagged Line");
    }

    private void jaggedP(final SnipeData v) {
        final IVector originClone = this.originCoords.clone().add(JaggedLineBrush.HALF_BLOCK_OFFSET);
        final IVector targetClone = this.targetCoords.clone().add(JaggedLineBrush.HALF_BLOCK_OFFSET);

        final IVector direction = targetClone.clone().subtract(originClone);
        final double length = this.targetCoords.distance(this.originCoords);

        if (length == 0) {
            this.currentPerformer.perform(this.targetCoords.getLocation(this.getWorld()).getBlock());
        } else {
            for (final BlockIterator iterator = new BlockIterator(((BukkitWorld)this.getWorld()).world(), ((BukkitVector)originClone).vector(), ((BukkitVector)direction).vector(), 0, NumberConversions.round(length)); iterator.hasNext();) {
                final Block block = iterator.next();
                for (int i = 0; i < recursion; i++) {
                    this.currentPerformer.perform(this.clampY(Math.round(block.getX() + this.random.nextInt(spread * 2) - spread), Math.round(block.getY() + this.random.nextInt(spread * 2) - spread), Math.round(block.getZ() + this.random.nextInt(spread * 2) - spread)));
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    public final void arrow(final SnipeData v) {
        if (originCoords == null) {
            originCoords = VectorFactory.getVector();
        }
        this.originCoords = this.getTargetBlock().getLocation().toVector();
        v.sendMessage(Messages.FIRST_POINT_SELECTED);
    }

    @Override
    public final void powder(final SnipeData v) {
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
            v.sendMessage(Messages.JAGGED_LINE_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
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
        } catch (NumberFormatException temp) {
temp.printStackTrace();
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
