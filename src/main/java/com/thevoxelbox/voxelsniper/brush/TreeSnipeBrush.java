package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.treeType.VoxelTreeType;

import java.util.Arrays;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Tree_Brush
 *
 * @author Mick
 */
public class TreeSnipeBrush extends Brush {

    private VoxelTreeType treeType = VoxelTreeType.TREE;

    /**
     *
     */
    public TreeSnipeBrush() {
        this.setName("Tree Snipe");
    }

    private void single(final SnipeData v,  IBlock targetBlock) {
        Undo undo = new Undo();

        IBlock blockBelow = targetBlock.getRelative(BlockFace.DOWN);
        IBlockState currentState = blockBelow.getState();
        undo.put(currentState);
        blockBelow.setMaterial(VoxelMaterial.GRASS_BLOCK, false);

        this.getWorld().generateTree(targetBlock.getLocation(), this.treeType, undo);

        blockBelow.setBlockData(currentState.getBlockData(), false);

        v.owner().storeUndo(undo);
    }

    private int getYOffset() {
        // getMaxHeight() is the same as getTargetBlock().getWorld().getMaxHeight()
        for (int i = 1; i < (getMaxHeight() - 1 - getTargetBlock().getY()); i++) {
            if (getTargetBlock().getRelative(0, i + 1, 0).getMaterial().isAir()) {
                return i;
            }
        }
        return 0;
    }

    private void printTreeType(final VoxelMessage vm) {
        TextComponent.Builder printout = Component.text();

        boolean delimiterHelper = true;
        for (final VoxelTreeType treeType : VoxelTreeType.values()) {
            if (treeType.isSupported()) {
                if (delimiterHelper) {
                    delimiterHelper = false;
                } else {
                    printout.append(Component.text(", ").color(NamedTextColor.WHITE));
                }
                printout.append(Component.text(treeType.name().toLowerCase()).color(treeType.equals(this.treeType) ? NamedTextColor.GRAY : NamedTextColor.DARK_GRAY));
            }
        }

        vm.custom(printout);
    }

    @Override
    protected final void arrow(final SnipeData v) {
         IBlock  targetBlock = getTargetBlock().getRelative(0, getYOffset(), 0);
        this.single(v, targetBlock);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.single(v, getTargetBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        this.printTreeType(vm);
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.TREESNIPE_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        try {
            this.treeType = VoxelTreeType.valueOf(params[0].toUpperCase());
            if (treeType.isSupported())
                this.printTreeType(v.getVoxelMessage());
            else
                throw new IllegalArgumentException("Not supported");
        } catch (Exception e) {
            v.getVoxelMessage().brushMessage(Messages.TREESNIPE_BRUSH_MESSAGE.replace("%triggerHandle%", triggerHandle));
        }
    }

    @Override
    public List<String> registerArguments() {

        return Arrays.stream(VoxelTreeType.values()).filter(VoxelTreeType::isSupported).map(Enum::name).toList();
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.treesnipe";
    }
}
