package com.thevoxelbox.voxelsniper.bukkit;

import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.player.AbstractPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.player.BukkitPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;


/**
 * @author Voxel
 */
public class VoxelSniperListener implements Listener {

    private static final String SNIPER_PERMISSION = "voxelsniper.sniper";
    private final BukkitVoxelSniper plugin;

    /**
     * @param plugin
     */
    public VoxelSniperListener(final BukkitVoxelSniper plugin) {
        this.plugin = plugin;
    }

    /**
     * @param event
     */
    @EventHandler(ignoreCancelled = false)
    public final void onPlayerInteract(final PlayerInteractEvent event) {
        AbstractPlayer player = new BukkitPlayer(event.getPlayer());

        if (!player.hasPermission(SNIPER_PERMISSION)) {
            return;
        }

        try {
            Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
            if (sniper.isEnabled() && sniper.snipe(event.getAction(), VoxelMaterial.getMaterial(event.getMaterial().getKey().getKey()), event.getClickedBlock() == null ? null : new BukkitBlock(event.getClickedBlock()), event.getBlockFace())) {
                event.setCancelled(true);
            }
        } catch (final Exception temp) {
temp.printStackTrace();
        }
    }

    /**
     * @param event
     */
    @EventHandler
    public final void onPlayerJoin(final PlayerJoinEvent event) {
        AbstractPlayer player = new BukkitPlayer(event.getPlayer());
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);

        if (player.hasPermission(SNIPER_PERMISSION) && plugin.getVoxelSniperConfiguration().isMessageOnLoginEnabled()) {
            sniper.displayInfo();
        }
    }
}
