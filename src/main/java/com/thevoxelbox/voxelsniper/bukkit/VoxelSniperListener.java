package com.thevoxelbox.voxelsniper.bukkit;

import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BlockFace;
import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.player.IPlayer;

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
    private final BukkitVoxelSniper plugin;
    private final List<UUID> cooldown = new ArrayList<>();

    /**
     * @param plugin The plugin
     */
    public VoxelSniperListener(final BukkitVoxelSniper plugin) {
        this.plugin = plugin;
    }

    /**
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public final void onPlayerInteract(final PlayerInteractEvent event) {
        if(event.getHand() != EquipmentSlot.HAND) return;
        IPlayer player = BukkitVoxelSniper.getInstance().getPlayer(event.getPlayer());

        if (!player.hasPermission(SNIPER_PERMISSION)) return;
        if(cooldown.contains(player.getUniqueId())) return;
        try {
            Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);
            if (sniper.isEnabled() && sniper.snipe(
                    Sniper.Action.valueOf(event.getAction().name()),
                    VoxelMaterial.getMaterial(event.getMaterial().getKey().getKey()),
                    event.getClickedBlock() != null ? new BukkitBlock(event.getClickedBlock()) : null,
                    BlockFace.valueOf(event.getBlockFace().name()))) {
                event.setCancelled(true);
                cooldown.add(player.getUniqueId());
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> cooldown.remove(player.getUniqueId()),1);
            }
        } catch (final Exception temp) {
            temp.printStackTrace();
        }
    }

    /**
     * @param event PlayerJoinEvent
     */
    @EventHandler
    public final void onPlayerJoin(final PlayerJoinEvent event) {
        IPlayer player = BukkitVoxelSniper.getInstance().getPlayer(event.getPlayer());
        Sniper sniper = VoxelProfileManager.getInstance().getSniperForPlayer(player);

        if (player.hasPermission(SNIPER_PERMISSION) && plugin.getVoxelSniperConfiguration().isMessageOnLoginEnabled()) {
            sniper.displayInfo();
        }
    }
    @EventHandler
    public final void onPlayerLeave(final PlayerQuitEvent event) {
        cooldown.remove(event.getPlayer().getUniqueId());
    }
}
