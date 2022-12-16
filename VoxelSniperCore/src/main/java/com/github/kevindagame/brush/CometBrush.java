package com.github.kevindagame.brush;

import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gavjenks Heavily revamped from ruler brush blockPositionY
 * @author Giltwist
 * @author Monofraps (Merged Meteor brush)
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#comet-brush">...</a>
 */
public class CometBrush extends AbstractBrush {

    private boolean useBigBalls = false;

    /**
     *
     */
    public CometBrush() {
        this.setName("Comet");
    }

    private void doFireball(final SnipeData v) {
        final VoxelVector targetCoords = new VoxelVector(this.getTargetBlock().getX() + .5 * this.getTargetBlock().getX() / Math.abs(this.getTargetBlock().getX()), this.getTargetBlock().getY() + .5, this.getTargetBlock().getZ() + .5 * this.getTargetBlock().getZ() / Math.abs(this.getTargetBlock().getZ()));
        positions.add(targetCoords.getLocation(this.getWorld()));
        positions.add(new VoxelLocation(this.getWorld(), this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ()));
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.COMET_BRUSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
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
    protected boolean actPerform(SnipeData v) {
        final VoxelVector targetCoords = new VoxelVector(this.getTargetBlock().getX() + .5 * this.getTargetBlock().getX() / Math.abs(this.getTargetBlock().getX()), this.getTargetBlock().getY() + .5, this.getTargetBlock().getZ() + .5 * this.getTargetBlock().getZ() / Math.abs(this.getTargetBlock().getZ()));
        final VoxelLocation playerLocation = v.owner().getPlayer().getEyeLocation();
        final VoxelVector slope = targetCoords.subtract(playerLocation.toVector());

        final VoxelEntityType type = (useBigBalls ? VoxelEntityType.FIREBALL : VoxelEntityType.SMALL_FIREBALL);
        v.owner().getPlayer().launchProjectile(type, slope.normalize());
        return true;
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
