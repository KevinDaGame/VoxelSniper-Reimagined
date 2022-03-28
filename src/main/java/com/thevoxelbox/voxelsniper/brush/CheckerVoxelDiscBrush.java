package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

/**
 * @author MikeMatrix
 */
public class CheckerVoxelDiscBrush extends PerformerBrush {

    private boolean useWorldCoordinates = true;

    /**
     * Default constructor.
     */
    public CheckerVoxelDiscBrush() {
        this.setName("Checker Voxel Disc");
    }

    /**
     * @param v
     * @param target
     */
    private void applyBrush(final SnipeData v, final Block target) {
        for (int x = v.getBrushSize(); x >= -v.getBrushSize(); x--) {
            for (int y = v.getBrushSize(); y >= -v.getBrushSize(); y--) {
                final int sum = this.useWorldCoordinates ? target.getX() + x + target.getZ() + y : x + y;
                if (sum % 2 != 0) {
                    this.currentPerformer.perform(this.clampY(target.getX() + x, target.getY(), target.getZ() + y, v.getWorld().getMinHeight()));
                }
            }
        }
        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.applyBrush(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.applyBrush(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {

        if (params[0].equals("info")) {
            v.sendMessage(ChatColor.GOLD + "Checker Voxel Disc Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " worldcoords -- Toggle to use World Coordinates or not (default: true)");
            return;
        }

        if (params[0].startsWith("worldcoords")) {
            this.useWorldCoordinates = !this.useWorldCoordinates;
            v.sendMessage(ChatColor.AQUA + "Using world coordinates: " + this.useWorldCoordinates);
            return;
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("worldcoords"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.checkervoxeldisc";
    }
}
