package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import java.util.Random;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Jagged_Line_Brush
 *
 * @author Giltwist
 * @author Monofraps
 */
public class JaggedLineBrush extends PerformerBrush {

    private static final Vector HALF_BLOCK_OFFSET = new Vector(0.5, 0.5, 0.5);
    private static int timesUsed = 0;

    private static final int RECURSION_MIN = 1;
    private static final int RECURSION_DEFAULT = 3;
    private static final int RECURSION_MAX = 10;
    private static final int SPREAD_DEFAULT = 3;

    private Random random = new Random();
    private Vector originCoords = null;
    private Vector targetCoords = new Vector();
    private int recursion = RECURSION_DEFAULT;
    private int spread = SPREAD_DEFAULT;

    /**
     *
     */
    public JaggedLineBrush() {
        this.setName("Jagged Line");
    }

    private void jaggedP(final SnipeData v) {
        final Vector originClone = this.originCoords.clone().add(JaggedLineBrush.HALF_BLOCK_OFFSET);
        final Vector targetClone = this.targetCoords.clone().add(JaggedLineBrush.HALF_BLOCK_OFFSET);

        final Vector direction = targetClone.clone().subtract(originClone);
        final double length = this.targetCoords.distance(this.originCoords);

        if (length == 0) {
            this.currentPerformer.perform(this.targetCoords.toLocation(this.getWorld()).getBlock());
        } else {
            for (final BlockIterator iterator = new BlockIterator(this.getWorld(), originClone, direction, 0, NumberConversions.round(length)); iterator.hasNext();) {
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
            originCoords = new Vector();
        }
        this.originCoords = this.getTargetBlock().getLocation().toVector();
        v.sendMessage(ChatColor.DARK_PURPLE + "First point selected.");
    }

    @Override
    public final void powder(final SnipeData v) {
        if (originCoords == null) {
            v.sendMessage(ChatColor.RED + "Warning: You did not select a first coordinate with the arrow");
        } else {
            this.targetCoords = this.getTargetBlock().getLocation().toVector();
            this.jaggedP(v);
        }

    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(ChatColor.GRAY + String.format("Recursion set to: %d", this.recursion));
        vm.custom(ChatColor.GRAY + String.format("Spread set to: %d", this.spread));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {

        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Jagged Line Brush Parameters: ");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " recursion [number] - sets the number of recursions (default 3, must be 1-10)");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " spread [number] - sets the spread (default 3, must be 1-10)");
            v.sendMessage(ChatColor.BLUE + "Instructions: Right click first point with the arrow. Right click with powder to draw a jagged line to set the second point.");
            return;
        }

        try {
            if (params[0].equalsIgnoreCase("recursion")) {
                int newRecursion = Integer.parseInt(params[1]);
                if (newRecursion >= RECURSION_MIN && newRecursion <= RECURSION_MAX) {
                    this.recursion = newRecursion;
                    v.sendMessage(ChatColor.GREEN + "Recursion set to: " + this.recursion);
                } else {
                    v.sendMessage(ChatColor.RED + "Recursion must be between " + RECURSION_MIN + " - " + RECURSION_MAX);
                }

                return;
            }

            if (params[0].equalsIgnoreCase("spread")) {
                final int newSpread = Integer.parseInt(params[1]);
                this.spread = newSpread;
                v.sendMessage(ChatColor.GREEN + "Spread set to: " + this.spread);
                return;
            }
        } catch (NumberFormatException e) {
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
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
