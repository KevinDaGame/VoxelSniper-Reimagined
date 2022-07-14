package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.BukkitEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.BukkitEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.IEntityType;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Entity_Brush
 *
 * @author Piotr
 */
public class EntityBrush extends Brush {

    private IEntityType entityType = new BukkitEntityType(EntityType.ZOMBIE);

    /**
     *
     */
    public EntityBrush() {
        this.setName("Entity");
    }

    private void spawn(final SnipeData v) {
        for (int x = 0; x < v.getBrushSize(); x++) {
            try {
                this.getWorld().spawn(this.getLastBlock().getLocation(), this.entityType);
            } catch (final IllegalArgumentException exception) {
                v.sendMessage(ChatColor.RED + "Cannot spawn entity!");
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.spawn(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.spawn(v);
    }

    @SuppressWarnings("deprecation")
    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushMessage(ChatColor.LIGHT_PURPLE + "Entity Brush" + " (" + this.entityType.getName() + ")");
        vm.size();
    }

    @SuppressWarnings("deprecation")
    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Entity Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " [entityType] -- Change brush to the specified entity type");
            return;
        }

        try {
            this.entityType = new BukkitEntityType(EntityType.valueOf(params[0]));
            v.sendMessage(ChatColor.GOLD + "Entity type: " + ChatColor.DARK_GREEN + this.entityType.getName());
        } catch (IllegalArgumentException e) {
            v.sendMessage(ChatColor.RED + "That entity type does not exist.");
        }
    }

    @Override
    public List<String> registerArguments() {
        List<String> entities = new ArrayList<>();

        for (EntityType entity : EntityType.values()) {
            entities.add(entity.name());
        }

        return entities;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.entity";
    }
}
