package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Clean_Snow_Brush
 *
 * @author psanker
 */
public class CleanSnowBrush extends Brush {

    public static final double SMOOTH_SPHERE_VALUE = 0.5;
    public static final int VOXEL_SPHERE_VALUE = 0;

    private boolean smoothSphere = false;

    /**
     *
     */
    public CleanSnowBrush() {
        this.setName("Clean Snow");
    }

    private void cleanSnow(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (this.smoothSphere ? SMOOTH_SPHERE_VALUE : VOXEL_SPHERE_VALUE), 2);
        final Undo undo = new Undo();

        for (int x = (brushSize + 1) * 2; x >= 0; x--) {
            final double xSquared = Math.pow(x - brushSize, 2);
            for (int z = (brushSize + 1) * 2; z >= 0; z--) {
                final double zSquared = Math.pow(z - brushSize, 2);

                for (int y = (brushSize + 1) * 2; y >= 0; y--) {
                    if ((xSquared + Math.pow(y - brushSize, 2) + zSquared) <= brushSizeSquared) {
                        Block b = this.clampY(this.getTargetBlock().getX() + x - brushSize, this.getTargetBlock().getY() + y - brushSize, this.getTargetBlock().getZ() + z - brushSize);
                        Block blockDown = this.clampY(this.getTargetBlock().getX() + x - brushSize, this.getTargetBlock().getY() + y - brushSize - 1, this.getTargetBlock().getZ() + z - brushSize);
                        if ((b.getMaterial() == new BukkitMaterial( Material.SNOW)) && ((blockDown.getMaterial() == new BukkitMaterial( Material.SNOW)) || (blockDown.getMaterial() == new BukkitMaterial( Material.AIR)))) {
                            setBlockType(b, new BukkitMaterial( Material.AIR), undo);
                        }

                    }
                }
            }
        }

        v.owner().storeUndo(undo);
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.cleanSnow(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.cleanSnow(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Clean Snow Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " smooth -- Toggle using smooth sphere algorithm (default: false)");
            return;
        }

        if (params[0].equalsIgnoreCase("smooth")) {
            this.smoothSphere = !this.smoothSphere;
            v.sendMessage(ChatColor.AQUA + "Smooth sphere algorithm: " + this.smoothSphere);
            return;
        }

        v.sendMessage(ChatColor.RED + "Invalid parameter! Use " + ChatColor.LIGHT_PURPLE + "'/b " + triggerHandle + " info'" + ChatColor.RED + " to display valid parameters.");
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("smooth"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.cleansnow";
    }
}
