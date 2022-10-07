package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.chunk.IChunk;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.entity.Painting.IPainting;
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType;
import com.github.kevindagame.voxelsniper.entity.player.IPlayer;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;


/**
 * @author Voxel
 */
public class BlockHelper {

    private static final double DEFAULT_STEP = 0.2;
    private static final int DEFAULT_RANGE = 250;
    private final IWorld world;
    private VoxelLocation startPoint;
    private VoxelLocation current;
    private VoxelLocation last;
    private VoxelVector direction;
    private double rangeSquared;
    private int maxWorldHeight;
    private int minWorldHeight;

    /**
     * Constructor requiring location, uses default values.
     *
     * @param location
     */
    public BlockHelper(final VoxelLocation location) {
        this(location, BlockHelper.DEFAULT_RANGE, BlockHelper.DEFAULT_STEP);
    }

    /**
     * Constructor requiring location, max range, and a stepping value.
     *
     * @param location
     * @param range
     * @param step
     */
    public BlockHelper(final VoxelLocation location, final double range, final double step) {
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
    public BlockHelper(final IPlayer player, final double range, final double step) {
        this(player.getEyeLocation(), range, step);
    }

    /**
     * Constructor requiring player, uses default values.
     *
     * @param player
     */
    public BlockHelper(final IPlayer player) {
        this(player, BlockHelper.DEFAULT_RANGE);
        // values
    }

    /**
     * @param player
     * @param range
     */
    public BlockHelper(final IPlayer player, final double range) {
        this(player, range, BlockHelper.DEFAULT_STEP);
    }

    /**
     * The painting method used to scroll or set a painting to a specific type.
     *
     * @param p      The player executing the method
     * @param auto   Scroll automatically? If false will use 'choice' to try and set the painting
     * @param back   Scroll in reverse?
     * @param choice Chosen index to set the painting to
     */
    public static void painting(final IPlayer p, final boolean auto, final boolean back, final int choice) {
        VoxelLocation targetLocation = new BlockHelper(p).getTargetBlock().getLocation();
        IChunk paintingChunk = targetLocation.getChunk();
        double bestDistanceMatch = 50.0;
        IPainting bestMatch = null;
        for (IEntity entity : paintingChunk.getEntities()) {
            if (entity.getType() == VoxelEntityType.PAINTING) {
                double distance = targetLocation.distanceSquared(entity.getLocation());
                if (distance <= 4 && distance < bestDistanceMatch) {
                    bestDistanceMatch = distance;
                    bestMatch = (IPainting) entity;
                }
            }
        }
        if (bestMatch != null) {
            if (auto) {
                try {
                    final int i = bestMatch.nextPaintingId(back);
                    if (bestMatch.setArtId(i)) {
                        p.sendMessage(Messages.PAINTING_SET.replace("%id%", String.valueOf(i)));
                    } else {
                        p.sendMessage(Messages.UNKNOWN_PAINTING.replace("%id%", i));
                    }
                } catch (final Exception e) {
                    p.sendMessage(Messages.ERROR);
                }
            } else {
                if (bestMatch.setArtId(choice)) {
                    p.sendMessage(Messages.PAINTING_SET.replace("%id%", String.valueOf(choice)));
                } else {
                    p.sendMessage(Messages.UNKNOWN_PAINTING.replace("%id%", choice));
                }
            }
        }
    }

    /**
     * Returns the current block along the line of vision.
     *
     * @return IBlock
     */
    public final IBlock getCurBlock() {
        if (this.current.distanceSquared(this.startPoint) > this.rangeSquared || this.current.getBlockY() >= maxWorldHeight || this.current.getBlockY() < minWorldHeight) {
            return null;
        }

        return this.world.getBlock(this.current);
    }

    /**
     * Returns the previous block along the line of vision.
     *
     * @return IBlock
     */
    public final IBlock getLastBlock() {
        if (this.current.distanceSquared(this.startPoint) > this.rangeSquared || this.last.getBlockY() >= maxWorldHeight || this.last.getBlockY() < minWorldHeight) {
            return null;
        }
        return this.world.getBlock(this.last);
    }

    /**
     * Returns STEPS forward along line of vision and returns block.
     *
     * @return IBlock
     */
    public final IBlock getNextBlock() {
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

        return this.world.getBlock(this.current);
    }

    /**
     * @return IBlock
     */
    public final IBlock getRangeBlock() {
        if (this.direction.lengthSquared() > this.rangeSquared) {
            return null;
        } else {
            return this.getRange();
        }
    }

    /**
     * Returns the block at the cursor, or null if out of range.
     *
     * @return IBlock
     */
    public final IBlock getTargetBlock() {
        IBlock current;
        while (((current = this.getNextBlock()) != null) && (current.getMaterial().isAir())) {

        }
        return this.getCurBlock();
    }

    private IBlock getRange() {
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
            if (!this.world.getBlock(this.current).getMaterial().isAir()) {
                return this.world.getBlock(this.current);
            }
            return this.getRange();
        }
    }

    private void init(final VoxelLocation location, final double range, final double step) {
        this.maxWorldHeight = world.getMaxWorldHeight();
        this.minWorldHeight = world.getMinWorldHeight();
        this.startPoint = location;

        this.current = location.clone();
        this.last = this.current;
        this.direction = location.getDirection();
        this.direction.normalize();
        this.direction.multiply(step);

        this.rangeSquared = range * range;
    }
}
