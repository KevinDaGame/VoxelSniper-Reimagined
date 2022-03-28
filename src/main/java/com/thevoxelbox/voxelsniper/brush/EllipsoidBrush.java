package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Ellipsoid_Brush
 *
 */
public class EllipsoidBrush extends PerformerBrush {

    private double xRad;
    private double yRad;
    private double zRad;
    private boolean istrue = false;

    /**
     *
     */
    public EllipsoidBrush() {
        this.setName("Ellipsoid");
    }

    private void execute(final SnipeData v, Block targetBlock) {
        this.currentPerformer.perform(targetBlock);
        double istrueoffset = istrue ? 0.5 : 0;
        int blockPositionX = targetBlock.getX();
        int blockPositionY = targetBlock.getY();
        int blockPositionZ = targetBlock.getZ();

        for (double x = 0; x <= xRad; x++) {

            final double xSquared = (x / (xRad + istrueoffset)) * (x / (xRad + istrueoffset));

            for (double z = 0; z <= zRad; z++) {

                final double zSquared = (z / (zRad + istrueoffset)) * (z / (zRad + istrueoffset));

                for (double y = 0; y <= yRad; y++) {

                    final double ySquared = (y / (yRad + istrueoffset)) * (y / (yRad + istrueoffset));

                    if (xSquared + ySquared + zSquared <= 1) {
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX + x), (int) (blockPositionY + y), (int) (blockPositionZ + z), v.getWorld().getMinHeight()));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX + x), (int) (blockPositionY + y), (int) (blockPositionZ - z), v.getWorld().getMinHeight()));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX + x), (int) (blockPositionY - y), (int) (blockPositionZ + z), v.getWorld().getMinHeight()));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX + x), (int) (blockPositionY - y), (int) (blockPositionZ - z), v.getWorld().getMinHeight()));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX - x), (int) (blockPositionY + y), (int) (blockPositionZ + z), v.getWorld().getMinHeight()));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX - x), (int) (blockPositionY + y), (int) (blockPositionZ - z), v.getWorld().getMinHeight()));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX - x), (int) (blockPositionY - y), (int) (blockPositionZ + z), v.getWorld().getMinHeight()));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX - x), (int) (blockPositionY - y), (int) (blockPositionZ - z), v.getWorld().getMinHeight()));
                    }

                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.execute(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.execute(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(ChatColor.AQUA + "X radius set to: " + ChatColor.DARK_AQUA + this.xRad);
        vm.custom(ChatColor.AQUA + "Y radius set to: " + ChatColor.DARK_AQUA + this.yRad);
        vm.custom(ChatColor.AQUA + "Z radius set to: " + ChatColor.DARK_AQUA + this.zRad);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Ellipse Brush Parameters: ");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " x [number]  -- Set X radius");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " y [number]  -- Set Y radius");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " z [number]  -- Set Z radius");
            return;
        }
        try {
            if (params[0].startsWith("x")) {
                this.xRad = Integer.parseInt(params[1]);
                v.sendMessage(ChatColor.AQUA + "X radius set to: " + this.xRad);
                return;
            }

            if (params[0].startsWith("y")) {
                this.yRad = Integer.parseInt(params[1]);
                v.sendMessage(ChatColor.AQUA + "Y radius set to: " + this.yRad);
                return;
            }

            if (params[0].startsWith("z")) {
                this.zRad = Integer.parseInt(params[1]);
                v.sendMessage(ChatColor.AQUA + "Z radius set to: " + this.zRad);
                return;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("x", "y", "z"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        HashMap<String, List<String>> argumentValues = new HashMap<>();
        
        argumentValues.put("x", Lists.newArrayList("[number]"));
        argumentValues.put("y", Lists.newArrayList("[number]"));
        argumentValues.put("z", Lists.newArrayList("[number]"));

        argumentValues.putAll(super.registerArgumentValues());
        return argumentValues;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.ellipsoid";
    }
}
