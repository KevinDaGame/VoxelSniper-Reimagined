package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformBrush;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Ellipsoid_Brush
 *
 */
public class EllipsoidBrush extends PerformBrush {

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
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX + x), (int) (blockPositionY + y), (int) (blockPositionZ + z)));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX + x), (int) (blockPositionY + y), (int) (blockPositionZ - z)));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX + x), (int) (blockPositionY - y), (int) (blockPositionZ + z)));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX + x), (int) (blockPositionY - y), (int) (blockPositionZ - z)));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX - x), (int) (blockPositionY + y), (int) (blockPositionZ + z)));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX - x), (int) (blockPositionY + y), (int) (blockPositionZ - z)));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX - x), (int) (blockPositionY - y), (int) (blockPositionZ + z)));
                        this.currentPerformer.perform(this.clampY((int) (blockPositionX - x), (int) (blockPositionY - y), (int) (blockPositionZ - z)));
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
    public final void info(final Message vm) {
        vm.brushName(this.getName());
        vm.custom(ChatColor.AQUA + "X radius set to: " + ChatColor.DARK_AQUA + this.xRad);
        vm.custom(ChatColor.AQUA + "Y radius set to: " + ChatColor.DARK_AQUA + this.yRad);
        vm.custom(ChatColor.AQUA + "Z radius set to: " + ChatColor.DARK_AQUA + this.zRad);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final com.thevoxelbox.voxelsniper.SnipeData v) {
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
    public void registerSubcommandArguments(HashMap<Integer, List<String>> subcommandArguments) {
        subcommandArguments.put(1, Lists.newArrayList("x", "y", "z"));

        super.registerSubcommandArguments(subcommandArguments); // super must always execute last!
    }

    @Override
    public void registerArgumentValues(String prefix, HashMap<String, HashMap<Integer, List<String>>> argumentValues) {
        HashMap<Integer, List<String>> arguments = new HashMap<>();
        arguments.put(1, Lists.newArrayList("[number]"));

        argumentValues.put(prefix + "x", arguments);
        argumentValues.put(prefix + "y", arguments);
        argumentValues.put(prefix + "z", arguments);
        
        super.registerArgumentValues(prefix, argumentValues);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.ellipsoid";
    }
}
