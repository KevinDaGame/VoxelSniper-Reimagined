package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.VectorFactory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.SmallFireball;

/**
 * @author Gavjenks Heavily revamped from ruler brush blockPositionY
 * @author Giltwist
 * @author Monofraps (Merged Meteor brush)
 */
public class CometBrush extends Brush {

    private boolean useBigBalls = false;

    /**
     *
     */
    public CometBrush() {
        this.setName("Comet");
    }

    private void doFireball(final SnipeData v) {
        final IVector targetCoords = VectorFactory.getVector(this.getTargetBlock().getX() + .5 * this.getTargetBlock().getX() / Math.abs(this.getTargetBlock().getX()), this.getTargetBlock().getY() + .5, this.getTargetBlock().getZ() + .5 * this.getTargetBlock().getZ() / Math.abs(this.getTargetBlock().getZ()));
        final ILocation playerLocation = v.owner().getPlayer().getEyeLocation();
        final IVector slope = targetCoords.subtract(playerLocation.toVector());

        if (useBigBalls) {
            v.owner().getPlayer().launchProjectile(LargeFireball.class).setVelocity(slope.normalize());
        } else {
            v.owner().getPlayer().launchProjectile(SmallFireball.class).setVelocity(slope.normalize());
        }
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.COMET_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
        }

        if (params[0].equalsIgnoreCase("big")) {
            useBigBalls = true;
            v.sendMessage(Messages.COMET_SIZE.replace("%size%", "BIG"));
            return;
        }

        if (params[0].equalsIgnoreCase("small")) {
            useBigBalls = false;
            v.sendMessage(Messages.COMET_SIZE.replace("%size%", "small"));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("big", "small"));
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.doFireball(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.doFireball(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.voxel();
        String size = (useBigBalls ? "BIG" : "small");
        vm.custom(Messages.COMET_SIZE.replace("%size%", size));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.comet";
    }
}
