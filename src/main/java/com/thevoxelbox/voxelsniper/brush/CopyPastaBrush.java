package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.Undo;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#CopyPasta_Brush
 *
 * @author giltwist
 */
public class CopyPastaBrush extends Brush {

    private static final int BLOCK_LIMIT = 10000;

    private boolean pasteAir = true; // False = no air, true = air
    private int points = 0; //
    private int numBlocks = 0;
    private int[] firstPoint = new int[3];
    private int[] secondPoint = new int[3];
    private int[] pastePoint = new int[3];
    private int[] minPoint = new int[3];
    private int[] offsetPoint = new int[3];

    private BlockData[] substanceArray;

    private int[] arraySize = new int[3];
    private int pivot = 0; // ccw degrees    

    /**
     *
     */
    public CopyPastaBrush() {
        this.setName("CopyPasta");
    }

    @SuppressWarnings("deprecation")
    private void doCopy(final SnipeData v) {
        for (int i = 0; i < 3; i++) {
            this.arraySize[i] = Math.abs(this.firstPoint[i] - this.secondPoint[i]) + 1;
            this.minPoint[i] = Math.min(this.firstPoint[i], this.secondPoint[i]);
            this.offsetPoint[i] = this.minPoint[i] - this.firstPoint[i]; // will always be negative or zero
        }

        this.numBlocks = (this.arraySize[0]) * (this.arraySize[1]) * (this.arraySize[2]);

        if (this.numBlocks > 0 && this.numBlocks < CopyPastaBrush.BLOCK_LIMIT) {
            this.substanceArray = new BlockData[this.numBlocks];

            for (int i = 0; i < this.arraySize[0]; i++) {
                for (int j = 0; j < this.arraySize[1]; j++) {
                    for (int k = 0; k < this.arraySize[2]; k++) {
                        final int currentPosition = i + this.arraySize[0] * j + this.arraySize[0] * this.arraySize[1] * k;
                        this.substanceArray[currentPosition] = this.getWorld().getBlockAt(this.minPoint[0] + i, this.minPoint[1] + j, this.minPoint[2] + k).getBlockData();
                    }
                }
            }

            v.sendMessage(ChatColor.AQUA + "" + this.numBlocks + " blocks copied.");
        } else {
            v.sendMessage(ChatColor.RED + "Copy area too big: " + this.numBlocks + "(Limit: " + CopyPastaBrush.BLOCK_LIMIT + ")");
        }
    }

    @SuppressWarnings("deprecation")
    private void doPasta(final SnipeData v) {
        final Undo undo = new Undo();

        for (int i = 0; i < this.arraySize[0]; i++) {
            for (int j = 0; j < this.arraySize[1]; j++) {
                for (int k = 0; k < this.arraySize[2]; k++) {
                    final int currentPosition = i + this.arraySize[0] * j + this.arraySize[0] * this.arraySize[1] * k;
                    Block block;

                    switch (this.pivot) {
                        case 180:
                            block = this.clampY(this.pastePoint[0] - this.offsetPoint[0] - i, this.pastePoint[1] + this.offsetPoint[1] + j, this.pastePoint[2] - this.offsetPoint[2] - k);
                            break;
                        case 270:
                            block = this.clampY(this.pastePoint[0] + this.offsetPoint[2] + k, this.pastePoint[1] + this.offsetPoint[1] + j, this.pastePoint[2] - this.offsetPoint[0] - i);
                            break;
                        case 90:
                            block = this.clampY(this.pastePoint[0] - this.offsetPoint[2] - k, this.pastePoint[1] + this.offsetPoint[1] + j, this.pastePoint[2] + this.offsetPoint[0] + i);
                            break;
                        default: // assume no rotation
                            block = this.clampY(this.pastePoint[0] + this.offsetPoint[0] + i, this.pastePoint[1] + this.offsetPoint[1] + j, this.pastePoint[2] + this.offsetPoint[2] + k);
                            break;
                    }

                    if (!(this.substanceArray[currentPosition].getMaterial() == Material.AIR && !this.pasteAir)) {
                        if (block.getType() != this.substanceArray[currentPosition].getMaterial() || !block.getBlockData().matches(this.substanceArray[currentPosition])) {
                            undo.put(block);
                        }
                        block.setBlockData(this.substanceArray[currentPosition], true);
                    }
                }
            }
        }
        v.sendMessage(ChatColor.AQUA + "" + this.numBlocks + " blocks pasted.");

        v.owner().storeUndo(undo);
    }

    @Override
    protected final void arrow(final com.thevoxelbox.voxelsniper.SnipeData v) {
        switch (this.points) {
            case 0:
                this.firstPoint[0] = this.getTargetBlock().getX();
                this.firstPoint[1] = this.getTargetBlock().getY();
                this.firstPoint[2] = this.getTargetBlock().getZ();
                v.sendMessage(ChatColor.GRAY + "First point");
                this.points = 1;
                break;
            case 1:
                this.secondPoint[0] = this.getTargetBlock().getX();
                this.secondPoint[1] = this.getTargetBlock().getY();
                this.secondPoint[2] = this.getTargetBlock().getZ();
                v.sendMessage(ChatColor.GRAY + "Second point");
                this.points = 2;
                break;
            default:
                this.firstPoint = new int[3];
                this.secondPoint = new int[3];
                this.numBlocks = 0;
                this.substanceArray = new BlockData[1];
                this.points = 0;
                v.sendMessage(ChatColor.GRAY + "Points cleared.");
                break;
        }
    }

    @Override
    protected final void powder(final com.thevoxelbox.voxelsniper.SnipeData v) {
        if (this.points == 2) {
            if (this.numBlocks == 0) {
                this.doCopy(v);
            } else if (this.numBlocks > 0 && this.numBlocks < CopyPastaBrush.BLOCK_LIMIT) {
                this.pastePoint[0] = this.getTargetBlock().getX();
                this.pastePoint[1] = this.getTargetBlock().getY();
                this.pastePoint[2] = this.getTargetBlock().getZ();
                this.doPasta(v);
            } else {
                v.sendMessage(ChatColor.RED + "Error");
            }
        } else {
            v.sendMessage(ChatColor.RED + "You must select exactly two points.");
        }
    }

    @Override
    public final void info(final Message vm) {
        vm.brushName(this.getName());
        vm.custom(ChatColor.GOLD + "Paste air: " + this.pasteAir);
        vm.custom(ChatColor.GOLD + "Pivot angle: " + this.pivot);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final com.thevoxelbox.voxelsniper.SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "CopyPasta Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " air  -- Toggle include air during paste (default: true)");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " rotate [number]  -- Set rotation pivot (default: 0)");
            return;
        }

        if (params[0].equalsIgnoreCase("air")) {
            this.pasteAir = !this.pasteAir;

            v.sendMessage(ChatColor.GOLD + "Air included in paste: " + this.pasteAir);
            return;
        }

        if (params[0].equalsIgnoreCase("rotate")) {
            if (params[1].equalsIgnoreCase("90") || params[1].equalsIgnoreCase("180") || params[1].equalsIgnoreCase("270") || params[1].equalsIgnoreCase("0")) {
                this.pivot = Integer.parseInt(params[1]);
                v.sendMessage(ChatColor.GOLD + "Pivot angle: " + this.pivot + " degrees");
                return;
            }
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
    }

    @Override
    public void registerSubcommandArguments(HashMap<Integer, List<String>> subcommandArguments) {
        subcommandArguments.put(1, Lists.newArrayList("rotate", "air"));

        super.registerSubcommandArguments(subcommandArguments); // super must always execute last!
    }

    @Override
    public void registerArgumentValues(String prefix, HashMap<String, HashMap<Integer, List<String>>> argumentValues) {
        HashMap<Integer, List<String>> arguments = new HashMap<>();
        arguments.put(1, Lists.newArrayList("0", "90", "180", "270"));

        argumentValues.put(prefix + "rotate", arguments);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.copypasta";
    }
}
