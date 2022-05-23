package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * The CloneStamp class is used to create a collection of blocks in a cylinder shape according to the selection the player has set.
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Clone_and_CopyPasta_Brushes
 *
 * @author Voxel
 */
public class CloneStampBrush extends StampBrush {

    /**
     *
     */
    public CloneStampBrush() {
        this.setName("Clone");
    }

    /**
     * The clone method is used to grab a snapshot of the selected area dictated blockPositionY targetBlock.x y z v.brushSize v.voxelHeight and v.cCen.
     * <p/>
     * x y z -- initial center of the selection v.brushSize -- the radius of the cylinder v.voxelHeight -- the heigth of the cylinder c.cCen -- the offset on
     * the Y axis of the selection ( bottom of the cylinder ) as blockPositionY: Bottom_Y = targetBlock.y + v.cCen;
     *
     * @param v the caller
     */
    private void clone(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        this.clone.clear();
        this.fall.clear();
        this.drop.clear();
        this.solid.clear();
        this.sorted = false;

        int yStartingPoint = this.getTargetBlock().getY() + v.getcCen();
        int yEndPoint = this.getTargetBlock().getY() + v.getVoxelHeight() + v.getcCen();

        if (yStartingPoint < this.getMinHeight()) {
            yStartingPoint = this.getMinHeight();
            v.sendMessage(ChatColor.DARK_PURPLE + "Warning: off-world start position.");
        } else if (yStartingPoint > this.getMaxHeight() - 1) {
            yStartingPoint = this.getMaxHeight() - 1;
            v.sendMessage(ChatColor.DARK_PURPLE + "Warning: off-world start position.");
        }

        if (yEndPoint < this.getMinHeight()) {
            yEndPoint = this.getMinHeight();
            v.sendMessage(ChatColor.DARK_PURPLE + "Warning: off-world end position.");
        } else if (yEndPoint > this.getMaxHeight() - 1) {
            yEndPoint = this.getMaxHeight() - 1;
            v.sendMessage(ChatColor.DARK_PURPLE + "Warning: off-world end position.");
        }

        final double bSquared = Math.pow(brushSize, 2);

        for (int z = yStartingPoint; z < yEndPoint; z++) {
            this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX(), z, this.getTargetBlock().getZ()), 0, z - yStartingPoint, 0, v.getWorld()));
            for (int y = 1; y <= brushSize; y++) {
                this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX(), z, this.getTargetBlock().getZ() + y), 0, z - yStartingPoint, y, v.getWorld()));
                this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX(), z, this.getTargetBlock().getZ() - y), 0, z - yStartingPoint, -y, v.getWorld()));
                this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX() + y, z, this.getTargetBlock().getZ()), y, z - yStartingPoint, 0, v.getWorld()));
                this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX() - y, z, this.getTargetBlock().getZ()), -y, z - yStartingPoint, 0, v.getWorld()));
            }
            for (int x = 1; x <= brushSize; x++) {
                final double xSquared = Math.pow(x, 2);
                for (int y = 1; y <= brushSize; y++) {
                    if ((xSquared + Math.pow(y, 2)) <= bSquared) {
                        this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX() + x, z, this.getTargetBlock().getZ() + y), x, z - yStartingPoint, y, v.getWorld()));
                        this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX() + x, z, this.getTargetBlock().getZ() - y), x, z - yStartingPoint, -y, v.getWorld()));
                        this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX() - x, z, this.getTargetBlock().getZ() + y), -x, z - yStartingPoint, y, v.getWorld()));
                        this.clone.add(new BlockWrapper(this.clampY(this.getTargetBlock().getX() - x, z, this.getTargetBlock().getZ() - y), -x, z - yStartingPoint, -y, v.getWorld()));
                    }
                }
            }
        }
        v.sendMessage(ChatColor.GREEN + String.valueOf(this.clone.size()) + ChatColor.AQUA + " blocks copied sucessfully.");
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.clone(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.height();
        vm.center();
        switch (this.stamp) {
            case DEFAULT:
                vm.brushMessage("Default Stamp");
                break;

            case NO_AIR:
                vm.brushMessage("No-Air Stamp");
                break;

            case FILL:
                vm.brushMessage("Fill Stamp");
                break;

            default:
                vm.custom(ChatColor.DARK_RED + "Error while stamping! Report");
                break;
        }
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Clone / Stamp Cylinder Brush Parameters: ");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " fill  -- Change to Fill mode");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " air  -- Change to No-Air mode");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " default  -- Change to Default mode");
            return;
        }

        if (params[0].equalsIgnoreCase("air")) {
            this.setStamp(StampType.NO_AIR);
            this.reSort();
            v.sendMessage(ChatColor.AQUA + "Stamp Mode: No-Air");
            return;
        }

        if (params[0].equalsIgnoreCase("fill")) {
            this.setStamp(StampType.FILL);
            this.reSort();
            v.sendMessage(ChatColor.AQUA + "Stamp Mode: Fill");
            return;
        }

        if (params[0].equalsIgnoreCase("default")) {
            this.setStamp(StampType.DEFAULT);
            this.reSort();
            v.sendMessage(ChatColor.AQUA + "StampMode: Default");
            return;
        }

        /**
         * TODO: Implement if (params[0].startsWith("centre")) { v.setcCen(Integer.parseInt(params[0].replace("c", ""))); v.sendMessage(ChatColor.BLUE + "Center
         * set to " + v.getcCen()); return; }
         */
        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("air", "fill", "default"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.clonestamp";
    }
}
