package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.snipe.Sniper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;


/**
 * @author Voxel
 */
public class VoxelSniperListener implements Listener {

    private static final String SNIPER_PERMISSION = "voxelsniper.sniper";
    private final VoxelSniper plugin;

    /**
     * @param plugin
     */
    public VoxelSniperListener(final VoxelSniper plugin) {
        this.plugin = plugin;
    }

    /**
     * @param event
     */
    @EventHandler(ignoreCancelled = false)
    public final void onPlayerInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(SNIPER_PERMISSION)) {
            return;
        }

        try {
            Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
            if (sniper.isEnabled() && sniper.snipe(event.getAction(), event.getMaterial(), event.getClickedBlock(), event.getBlockFace())) {
                event.setCancelled(true);
            }
        } catch (final Exception ignored) {
        }
    }

    /**
     * @param event
     */
    @EventHandler
    public final void onPlayerJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);

        if (player.hasPermission(SNIPER_PERMISSION) && plugin.getVoxelSniperConfiguration().isMessageOnLoginEnabled()) {
            sniper.displayInfo();
        }
    }
}
