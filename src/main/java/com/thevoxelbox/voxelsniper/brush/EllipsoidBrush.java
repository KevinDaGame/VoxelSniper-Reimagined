package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.brush.perform.PerformerBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.util.VoxelMessage;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Ellipsoid_Brush
 */
public class EllipsoidBrush extends PerformerBrush {

    private final boolean istrue = false;
    private double xRad;
    private double yRad;
    private double zRad;

    /**
     *
     */
    public EllipsoidBrush() {
        this.setName("Ellipsoid");
    }

    private void execute(final SnipeData v, IBlock targetBlock) {
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
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.AXIS_SET_TO_VALUE.replace("%axis%", "X").replace("%value%", String.valueOf(this.xRad)));
        vm.custom(Messages.AXIS_SET_TO_VALUE.replace("%axis%", "Y").replace("%value%", String.valueOf(this.yRad)));
        vm.custom(Messages.AXIS_SET_TO_VALUE.replace("%axis%", "Z").replace("%value%", String.valueOf(this.zRad)));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final com.thevoxelbox.voxelsniper.snipe.SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.ELLIPSOID_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }
        try {
            if (params[0].startsWith("x")) {
                this.xRad = Integer.parseInt(params[1]);
                v.sendMessage(Messages.AXIS_SET_TO_VALUE.replace("%axis%", "X").replace("%value%", String.valueOf(this.xRad)));
                return;
            }

            if (params[0].startsWith("y")) {
                this.yRad = Integer.parseInt(params[1]);
                v.sendMessage(Messages.AXIS_SET_TO_VALUE.replace("%axis%", "Y").replace("%value%", String.valueOf(this.yRad)));
                return;
            }

            if (params[0].startsWith("z")) {
                this.zRad = Integer.parseInt(params[1]);
                v.sendMessage(Messages.AXIS_SET_TO_VALUE.replace("%axis%", "Z").replace("%value%", String.valueOf(this.zRad)));
                return;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException temp) {
            temp.printStackTrace();
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
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
