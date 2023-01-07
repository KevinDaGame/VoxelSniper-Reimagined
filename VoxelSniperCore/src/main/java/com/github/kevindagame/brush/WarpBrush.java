package com.github.kevindagame.brush;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.location.BaseLocation;

/**
 * @author MikeMatrix
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#warp-brush">...</a>
 */
public class WarpBrush extends AbstractBrush {

    /**
     *
     */
    public WarpBrush() {
        this.setName("Warp");
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        IPlayer player = v.owner().getPlayer();
        BaseLocation location = this.getLastBlock().getLocation();
        BaseLocation playerLocation = player.getLocation();
        location.setPitch(playerLocation.getPitch());
        location.setYaw(playerLocation.getYaw());

        player.teleport(location);
    }

    @Override
    protected final void powder(final SnipeData v) {
        IPlayer player = v.owner().getPlayer();
        BaseLocation location = this.getLastBlock().getLocation();
        BaseLocation playerLocation = player.getLocation();
        location.setPitch(playerLocation.getPitch());
        location.setYaw(playerLocation.getYaw());

        getWorld().strikeLightning(location);
        player.teleport(location);
        getWorld().strikeLightning(location);
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.warp";
    }
}
