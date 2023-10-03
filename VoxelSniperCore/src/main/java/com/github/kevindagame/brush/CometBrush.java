package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.Utils;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.util.brushOperation.operation.CustomOperation;
import com.github.kevindagame.util.brushOperation.operation.CustomOperationContext;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gavjenks Heavily revamped from ruler brush blockPositionY
 * @author Giltwist
 * @author Monofraps (Merged Meteor brush)
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#comet-brush">...</a>
 */
public class CometBrush extends CustomBrush {

    private boolean useBigBalls = false;


    private void doFireball(final SnipeData v) {
        final VoxelVector targetCoords = new VoxelVector(this.getTargetBlock().getX() + .5 * this.getTargetBlock().getX() / Math.abs(this.getTargetBlock().getX()), this.getTargetBlock().getY() + .5, this.getTargetBlock().getZ() + .5 * this.getTargetBlock().getZ() / Math.abs(this.getTargetBlock().getZ()));
        addOperation(new CustomOperation(targetCoords.getLocation(getWorld()), this, v, CustomOperationContext.TARGETLOCATION));
        addOperation(new CustomOperation(v.owner().getPlayer().getEyeLocation(), this, v, CustomOperationContext.PLAYERLOCATION));

    }

    @Override
    public final void parseParameters(@NotNull final String triggerHandle, final String[] params, @NotNull final SnipeData v) {
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

    @NotNull
    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Utils.newArrayList("big", "small"));
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
    public boolean perform(List<CustomOperation> operations, @NotNull SnipeData snipeData, @NotNull Undo undo) {
        if (operations.size() != 2) {
            return false;
        }
        final var targetCoords = operations.stream().filter(operation -> operation.getContext() == CustomOperationContext.TARGETLOCATION).findFirst();
        final var playerCoords = operations.stream().filter(operation -> operation.getContext() == CustomOperationContext.PLAYERLOCATION).findFirst();
        if (targetCoords.isEmpty() || playerCoords.isEmpty()) return false;
        final VoxelVector slope = targetCoords.get().getLocation().toVector().subtract(playerCoords.get().getLocation().toVector());

        final VoxelEntityType type = (useBigBalls ? VoxelEntityType.FIREBALL() : VoxelEntityType.SMALL_FIREBALL());
        snipeData.owner().getPlayer().launchProjectile(type, slope.normalize());
        return true;

    }
}
