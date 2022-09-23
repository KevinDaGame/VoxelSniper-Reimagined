package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.VoxelSniper;

import net.kyori.adventure.audience.Audience;

import org.bukkit.Art;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * @author Voxel
 */
public class BlockHelper {

    private static final double DEFAULT_STEP = 0.1;
    private static final int DEFAULT_RANGE = 250;
    private final World world;
    private Location startPoint;
    private Location current;
    private Location last;
    private Vector direction;
    private double rangeSquared;
    private int maxWorldHeight;
    private int minWorldHeight;

    /**
     * Constructor requiring location, uses default values.
     *
     * @param location
     */
    public BlockHelper(final Location location) {
        this(location, BlockHelper.DEFAULT_RANGE, BlockHelper.DEFAULT_STEP);
    }

    /**
     * Constructor requiring location, max range, and a stepping value.
     *
     * @param location
     * @param range
     * @param step
     */
    public BlockHelper(final Location location, final double range, final double step) {
        this.world = location.getWorld();
        this.init(location, range, step);
    }

    /**
     * Constructor requiring player, max range, and a stepping value.
     *
     * @param player
     * @param range
     * @param step
     */
    public BlockHelper(final Player player, final double range, final double step) {
        this(player.getEyeLocation(), range, step);
    }

    /**
     * Constructor requiring player, uses default values.
     *
     * @param player
     */
    public BlockHelper(final Player player) {
        this(player, BlockHelper.DEFAULT_RANGE);
        // values
    }

    /**
     * @param player
     * @param range
     */
    public BlockHelper(final Player player, final double range) {
        this(player, range, BlockHelper.DEFAULT_STEP);
    }

    /**
     * Returns the current block along the line of vision.
     *
     * @return Block
     */
    public final Block getCurBlock() {
        if (this.current.distanceSquared(this.startPoint) > this.rangeSquared || this.current.getBlockY() >= maxWorldHeight || this.current.getBlockY() < minWorldHeight) {
            return null;
        }

        return this.world.getBlockAt(this.current);
    }

    /**
     * Returns the previous block along the line of vision.
     *
     * @return Block
     */
    public final Block getLastBlock() {
        if (this.current.distanceSquared(this.startPoint) > this.rangeSquared || this.last.getBlockY() >= maxWorldHeight || this.last.getBlockY() < minWorldHeight) {
            return null;
        }
        return this.world.getBlockAt(this.last);
    }

    /**
     * Returns STEPS forward along line of vision and returns block.
     *
     * @return Block
     */
    public final Block getNextBlock() {
        this.last = this.current.clone();
        int lastX = this.last.getBlockX();
        int lastY = this.last.getBlockY();
        int lastZ = this.last.getBlockZ();

        do {
            this.current.add(this.direction);

        } while (((this.current.getBlockX() == lastX) && (this.current.getBlockY() == lastY) && (this.current.getBlockZ() == lastZ)));

        if (this.current.distanceSquared(this.startPoint) > this.rangeSquared || this.current.getBlockY() >= maxWorldHeight || this.current.getBlockY() < minWorldHeight) {
            return null;
        }

        return this.world.getBlockAt(this.current);
    }

    /**
     * @return Block
     */
    public final Block getRangeBlock() {
        if (this.direction.lengthSquared() > this.rangeSquared) {
            return null;
        } else {
            return this.getRange();
        }
    }

    /**
     * Returns the block at the cursor, or null if out of range.
     *
     * @return Block
     */
    public final Block getTargetBlock() {
        Block current;
        while (((current = this.getNextBlock()) != null) && (current.getType().isAir())) {

        }
        return this.getCurBlock();
    }

    private Block getRange() {
        this.last = this.current.clone();
        int lastX = this.last.getBlockX();
        int lastY = this.last.getBlockY();
        int lastZ = this.last.getBlockZ();

        do {
            this.current.add(this.direction);

        } while (((this.current.getBlockX() == lastX) && (this.current.getBlockY() == lastY) && (this.current.getBlockZ() == lastZ)));

        if (this.current.distanceSquared(this.startPoint) > this.rangeSquared || this.current.getBlockY() >= maxWorldHeight || this.current.getBlockY() < minWorldHeight) {
            return getLastBlock();
        } else {
            if (!this.world.getBlockAt(this.current).getType().isAir()) {
                return this.world.getBlockAt(this.current);
            }
            return this.getRange();
        }
    }

    private void init(final Location location, final double range, final double step) {
        this.maxWorldHeight = world.getMaxHeight();
        this.minWorldHeight = world.getMinHeight();
        this.startPoint = location;

        this.current = location.clone();
        this.last = this.current;
        this.direction = location.getDirection().normalize().multiply(step);
        this.rangeSquared = range*range;
    }

    /**
     * The painting method used to scroll or set a painting to a specific type.
     *
     * @param p The player executing the method
     * @param auto Scroll automatically? If false will use 'choice' to try and set the painting
     * @param back Scroll in reverse?
     * @param choice Chosen index to set the painting to
     */
    @SuppressWarnings(value = "deprecation")
    public static void painting(final Player p, final boolean auto, final boolean back, final int choice) {
        Location targetLocation = p.getTargetBlock(null, 4).getLocation();
        Chunk paintingChunk = p.getTargetBlock(null, 4).getLocation().getChunk();
        double bestDistanceMatch = 50.0;
        Painting bestMatch = null;
        for (Entity entity : paintingChunk.getEntities()) {
            if (entity.getType() == EntityType.PAINTING) {
                double distance = targetLocation.distanceSquared(entity.getLocation());
                if (distance <= 4 && distance < bestDistanceMatch) {
                    bestDistanceMatch = distance;
                    bestMatch = (Painting) entity;
                }
            }
        }
        if (bestMatch != null) {
            if (auto) {
                try {
                    final int i = (bestMatch.getArt().getId() + (back ? -1 : 1) + Art.values().length) % Art.values().length;
                    Art art = Art.getById(i);
                    if (art == null) {
                        audience(p).sendMessage(Messages.FINAL_PAINTING);
                        return;
                    }
                    bestMatch.setArt(art);
                    audience(p).sendMessage(Messages.PAINTING_SET.replace("%id%", String.valueOf(i)));
                } catch (final Exception e) {
                    audience(p).sendMessage(Messages.ERROR);
                }
            } else {
                try {
                    Art art = Art.getById(choice);
                    bestMatch.setArt(art);
                    audience(p).sendMessage(Messages.PAINTING_SET.replace("%id%", String.valueOf(choice)));
                } catch (final Exception exception) {
                    audience(p).sendMessage(Messages.INVALID_INPUT);
                }
            }
        }
    }

    private static @NotNull Audience audience(Player p) {
        return VoxelSniper.getAdventure().player(p);
    }
}
