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
 * @author Voxel
 */
public class FillDownBrush extends PerformerBrush {

    private static final double SMOOTH_CIRCLE_VALUE = 0.5;
    private static final double VOXEL_CIRCLE_VALUE = 0.0;

    private boolean smoothCircle = false;

    private boolean fillLiquid = true;
    private boolean fromExisting = false;

    /**
     *
     */
    public FillDownBrush() {
        this.setName("Fill Down");
    }

    private void fillDown(final SnipeData v, final Block b) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (smoothCircle ? SMOOTH_CIRCLE_VALUE : VOXEL_CIRCLE_VALUE), 2);
        final Block targetBlock = this.getTargetBlock();
        for (int x = -brushSize; x <= brushSize; x++) {
            final double currentXSquared = Math.pow(x, 2);

            for (int z = -brushSize; z <= brushSize; z++) {
                if (currentXSquared + Math.pow(z, 2) <= brushSizeSquared) {
                    int y = 0;
                    boolean found = false;
                    if (this.fromExisting) {
                        for (y = -v.getVoxelHeight(); y < v.getVoxelHeight(); y++) {
                            final Block currentBlock = this.getWorld().getBlockAt(
                                    targetBlock.getX() + x,
                                    targetBlock.getY() + y,
                                    targetBlock.getZ() + z);
                            if (!currentBlock.isEmpty()) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            continue;
                        }
                        y--;
                    }
                    for (; y >= -targetBlock.getY(); --y) {
                        final Block currentBlock = this.getWorld().getBlockAt(
                                targetBlock.getX() + x,
                                targetBlock.getY() + y,
                                targetBlock.getZ() + z);
                        if (currentBlock.isEmpty() || (fillLiquid && currentBlock.isLiquid())) {
                            this.currentPerformer.perform(currentBlock);
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.fillDown(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.fillDown(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Fill Down Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " smooth  -- Toggle use smooth circles (default: false)");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " liquid  -- Toggle filling liquids (default: true)");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " existing  -- Toggle filling existing blocks or all blocks. (Toggle)");
            return;
        }

        if (params[0].equalsIgnoreCase("liquid")) {
            this.fillLiquid = !this.fillLiquid;
            v.sendMessage(ChatColor.AQUA + "Now filling " + ((this.fillLiquid) ? "liquid and air" : "air only") + ".");
            return;
        }

        if (params[0].equalsIgnoreCase("existing")) {
            this.fromExisting = !this.fromExisting;
            v.sendMessage(ChatColor.AQUA + "Now filling down from " + ((this.fromExisting) ? "existing" : "all") + " blocks.");
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
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("smooth", "liquid", "existing"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.filldown";
    }
}
