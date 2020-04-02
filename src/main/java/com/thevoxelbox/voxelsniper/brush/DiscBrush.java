package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformBrush;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Disc_Brush
 *
 * @author Voxel
 */
public class DiscBrush extends PerformBrush {

    private static final double SMOOTH_CIRCLE_VALUE = 0.5;
    private static final double VOXEL_CIRCLE_VALUE = 0.0;

    private boolean smoothCircle = false;

    /**
     * Default Constructor.
     */
    public DiscBrush() {
        this.setName("Disc");
    }

    /**
     * Disc executor.
     *
     * @param v
     */
    private void disc(final SnipeData v, final Block targetBlock) {
        final double radiusSquared = (v.getBrushSize() + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE)) * (v.getBrushSize() + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE));
        final Vector centerPoint = targetBlock.getLocation().toVector();
        final Vector currentPoint = centerPoint.clone();

        for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++) {
            currentPoint.setX(centerPoint.getX() + x);
            for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++) {
                currentPoint.setZ(centerPoint.getZ() + z);
                if (centerPoint.distanceSquared(currentPoint) <= radiusSquared) {
                    this.currentPerformer.perform(this.clampY(currentPoint.getBlockX(), currentPoint.getBlockY(), currentPoint.getBlockZ()));
                }
            }
        }
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.disc(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.disc(v, this.getLastBlock());
    }

    @Override
    public final void info(final Message vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Disc Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " smooth  -- Toggle smooth circle (default: false)");
            return;
        }

        if (params[0].startsWith("smooth")) {
            this.smoothCircle = !this.smoothCircle;
            v.sendMessage(ChatColor.AQUA + "Using smooth circle: " + this.smoothCircle);
            return;
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public void registerSubcommandArguments(HashMap<Integer, List<String>> subcommandArguments) {
        subcommandArguments.put(1, Lists.newArrayList("smooth"));

        super.registerSubcommandArguments(subcommandArguments); // super must always execute last!
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.disc";
    }
}
