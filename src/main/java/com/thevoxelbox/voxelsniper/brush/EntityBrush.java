package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Entity_Brush
 *
 * @author Piotr
 */
public class EntityBrush extends Brush {

    private EntityType entityType = EntityType.ZOMBIE;

    /**
     *
     */
    public EntityBrush() {
        this.setName("Entity");
    }

    private void spawn(final SnipeData v) {
        for (int x = 0; x < v.getBrushSize(); x++) {
            try {
                this.getWorld().spawn(this.getLastBlock().getLocation(), this.entityType.getEntityClass());
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
            this.entityType = EntityType.valueOf(params[0]);
            v.sendMessage(ChatColor.GOLD + "Entity type: " + ChatColor.DARK_GREEN + this.entityType.name());
        } catch (IllegalArgumentException e) {
            v.sendMessage(ChatColor.RED + "That entity type does not exist.");
        }
    }

    @Override
    public HashMap<String, List<String>> registerArguments(String brushHandle) {
        HashMap<String, List<String>> arguments = new HashMap<>();
        List<String> entities = new ArrayList<>();

        for (EntityType entity : EntityType.values()) {
            entities.add(entity.name());
        }

        arguments.put(BRUSH_ARGUMENT_PREFIX + brushHandle, entities);
        
        return arguments;
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.entity";
    }
}
