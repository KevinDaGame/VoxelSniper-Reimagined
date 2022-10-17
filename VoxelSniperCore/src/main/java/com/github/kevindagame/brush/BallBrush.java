package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * A brush that creates a solid ball. http://www.voxelwiki.com/minecraft/Voxelsniper#The_Ball_Brush
 *
 * @author Piotr
 */
public class BallBrush extends PerformerBrush {

    public static final double SMOOTH_SPHERE_VALUE = 0.5;
    public static final int VOXEL_SPHERE_VALUE = 0;

    private boolean smoothSphere = false;

    /**
     *
     */
    public BallBrush() {
        this.setName("Ball");
    }

    private void ball(final SnipeData v, IBlock targetBlock) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize + (this.smoothSphere ? SMOOTH_SPHERE_VALUE : VOXEL_SPHERE_VALUE), 2);

        int blockPositionX = targetBlock.getX();
        int blockPositionY = targetBlock.getY();
        int blockPositionZ = targetBlock.getZ();
        this.currentPerformer.perform(targetBlock);

        for (int z = 1; z <= brushSize; z++) {
            final double zSquared = Math.pow(z, 2);

            this.currentPerformer.perform(this.clampY(blockPositionX + z, blockPositionY, blockPositionZ));
            this.currentPerformer.perform(this.clampY(blockPositionX - z, blockPositionY, blockPositionZ));
            this.currentPerformer.perform(this.clampY(blockPositionX, blockPositionY + z, blockPositionZ));
            this.currentPerformer.perform(this.clampY(blockPositionX, blockPositionY - z, blockPositionZ));
            this.currentPerformer.perform(this.clampY(blockPositionX, blockPositionY, blockPositionZ + z));
            this.currentPerformer.perform(this.clampY(blockPositionX, blockPositionY, blockPositionZ - z));

            for (int x = 1; x <= brushSize; x++) {
                final double xSquared = Math.pow(x, 2);

                if (zSquared + xSquared <= brushSizeSquared) {
                    this.currentPerformer.perform(this.clampY(blockPositionX + z, blockPositionY, blockPositionZ + x));
                    this.currentPerformer.perform(this.clampY(blockPositionX + z, blockPositionY, blockPositionZ - x));
                    this.currentPerformer.perform(this.clampY(blockPositionX - z, blockPositionY, blockPositionZ + x));
                    this.currentPerformer.perform(this.clampY(blockPositionX - z, blockPositionY, blockPositionZ - x));
                    this.currentPerformer.perform(this.clampY(blockPositionX + z, blockPositionY + x, blockPositionZ));
                    this.currentPerformer.perform(this.clampY(blockPositionX + z, blockPositionY - x, blockPositionZ));
                    this.currentPerformer.perform(this.clampY(blockPositionX - z, blockPositionY + x, blockPositionZ));
                    this.currentPerformer.perform(this.clampY(blockPositionX - z, blockPositionY - x, blockPositionZ));
                    this.currentPerformer.perform(this.clampY(blockPositionX, blockPositionY + z, blockPositionZ + x));
                    this.currentPerformer.perform(this.clampY(blockPositionX, blockPositionY + z, blockPositionZ - x));
                    this.currentPerformer.perform(this.clampY(blockPositionX, blockPositionY - z, blockPositionZ + x));
                    this.currentPerformer.perform(this.clampY(blockPositionX, blockPositionY - z, blockPositionZ - x));
                }

                for (int y = 1; y <= brushSize; y++) {
                    if ((xSquared + Math.pow(y, 2) + zSquared) <= brushSizeSquared) {
                        this.currentPerformer.perform(this.clampY(blockPositionX + x, blockPositionY + y, blockPositionZ + z));
                        this.currentPerformer.perform(this.clampY(blockPositionX + x, blockPositionY + y, blockPositionZ - z));
                        this.currentPerformer.perform(this.clampY(blockPositionX - x, blockPositionY + y, blockPositionZ + z));
                        this.currentPerformer.perform(this.clampY(blockPositionX - x, blockPositionY + y, blockPositionZ - z));
                        this.currentPerformer.perform(this.clampY(blockPositionX + x, blockPositionY - y, blockPositionZ + z));
                        this.currentPerformer.perform(this.clampY(blockPositionX + x, blockPositionY - y, blockPositionZ - z));
                        this.currentPerformer.perform(this.clampY(blockPositionX - x, blockPositionY - y, blockPositionZ + z));
                        this.currentPerformer.perform(this.clampY(blockPositionX - x, blockPositionY - y, blockPositionZ - z));
                    }
                }
            }
        }

        v.owner().storeUndo(this.currentPerformer.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.ball(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.ball(v, this.getLastBlock());
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.BALLBRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("smooth")) {
            this.smoothSphere = !this.smoothSphere;
            v.sendMessage(Messages.SMOOTHSPHERE_ALGORITHM.replace("%smoothSphere%", String.valueOf(this.smoothSphere)));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.addAll(Lists.newArrayList("smooth"));

        arguments.addAll(super.registerArguments());
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.ball";
    }
}
