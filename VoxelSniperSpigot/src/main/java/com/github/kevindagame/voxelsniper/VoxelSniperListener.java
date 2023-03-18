package com.github.kevindagame.voxelsniper;

import com.github.kevindagame.snipe.Sniper;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.SpigotBlock;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author Voxel
 */
public class VoxelSniperListener implements Listener {

    private static final String SNIPER_PERMISSION = "voxelsniper.sniper";
    private final SpigotVoxelSniper plugin;
    private final List<UUID> cooldown = new ArrayList<>();

    /**
     * @param plugin The plugin
     */
    public VoxelSniperListener(final SpigotVoxelSniper plugin) {
        this.plugin = plugin;
    }

    /**
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public final void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        IPlayer player = SpigotVoxelSniper.getInstance().getPlayer(event.getPlayer());

        if (!player.hasPermission(SNIPER_PERMISSION)) return;
        if (cooldown.contains(player.getUniqueId())) return;
        cooldown.add(player.getUniqueId());
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> cooldown.remove(player.getUniqueId()), 1);
        try {
            Sniper sniper = player.getSniper();
            if (sniper.isEnabled() && sniper.snipe(
                    Sniper.Action.valueOf(event.getAction().name()),
                    VoxelMaterial.getMaterial(event.getMaterial().getKey().getKey()),
                    event.getClickedBlock() != null ? new SpigotBlock(event.getClickedBlock()) : null,
                    BlockFace.valueOf(event.getBlockFace().name()))) {
                event.setCancelled(true);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param event PlayerJoinEvent
     */
    @EventHandler
    public final void onPlayerJoin(final PlayerJoinEvent event) {
        IPlayer player = SpigotVoxelSniper.getInstance().getPlayer(event.getPlayer());
        Sniper sniper = player.getSniper();

        if (player.hasPermission(SNIPER_PERMISSION) && plugin.getVoxelSniperConfiguration().isMessageOnLoginEnabled()) {
            sniper.displayInfo();
        }
    }

    @EventHandler
    public final void onPlayerLeave(final PlayerQuitEvent event) {
        cooldown.remove(event.getPlayer().getUniqueId());
    }
}
