package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.chunk.IChunk;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.BukkitEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.player.AbstractPlayer;
import com.thevoxelbox.voxelsniper.voxelsniper.world.IWorld;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;

/**
 * @author Voxel
 */
public class BlockHelper {

    private static final double DEFAULT_PLAYER_VIEW_HEIGHT = 1.65;
    private static final double DEFAULT_LOCATION_VIEW_HEIGHT = 0;
    private static final double DEFAULT_STEP = 0.2;
    private static final int DEFAULT_RANGE = 250;

    private ILocation playerLoc;
    private double rotX, rotY, viewHeight, rotXSin, rotXCos, rotYSin, rotYCos;
    private double length, hLength, step;
    private double range;
    private double playerX, playerY, playerZ;
    private double xOffset, yOffset, zOffset;
    private int lastX, lastY, lastZ;
    private int targetX, targetY, targetZ;
    private IWorld world;
    private int maxWorldHeight;
    private int minWorldHeight;

    /**
     * Constructor requiring location, uses default values.
     *
     * @param location
     */
    public BlockHelper(final ILocation location) {
        this.init(location, BlockHelper.DEFAULT_RANGE, BlockHelper.DEFAULT_STEP, BlockHelper.DEFAULT_LOCATION_VIEW_HEIGHT);
    }

    /**
     * Constructor requiring location, max range, and a stepping value.
     *
     * @param location
     * @param range
     * @param step
     */
    public BlockHelper(final ILocation location, final int range, final double step) {
        this.world = location.getWorld();
        this.init(location, range, step, BlockHelper.DEFAULT_LOCATION_VIEW_HEIGHT);
    }

    /**
     * Constructor requiring player, max range, and a stepping value.
     *
     * @param player
     * @param range
     * @param step
     */
    public BlockHelper(final AbstractPlayer player, final int range, final double step) {
        this.init(player.getLocation(), range, step, BlockHelper.DEFAULT_PLAYER_VIEW_HEIGHT);
    }

    /**
     * Constructor requiring player, uses default values.
     *
     * @param player
     * @param world
     */
    public BlockHelper(final AbstractPlayer player, final IWorld world) {
        this.world = world;
        this.init(player.getLocation(), BlockHelper.DEFAULT_RANGE, BlockHelper.DEFAULT_STEP, BlockHelper.DEFAULT_PLAYER_VIEW_HEIGHT);
        // values
    }

    /**
     * @param player
     * @param world
     * @param range
     */
    public BlockHelper(final AbstractPlayer player, final IWorld world, final double range) {
        this.world = world;
        this.init(player.getLocation(), range, BlockHelper.DEFAULT_STEP, BlockHelper.DEFAULT_PLAYER_VIEW_HEIGHT);
        this.fromOffworld();
    }

    /**
     *
     */
    public final void fromOffworld() {
        if (this.targetY > maxWorldHeight) {
            while (this.targetY > maxWorldHeight && this.length <= this.range) {
                this.lastX = this.targetX;
                this.lastY = this.targetY;
                this.lastZ = this.targetZ;

                do {
                    this.length += this.step;

                    this.hLength = (this.length * this.rotYCos);
                    this.yOffset = (this.length * this.rotYSin);
                    this.xOffset = (this.hLength * this.rotXCos);
                    this.zOffset = (this.hLength * this.rotXSin);

                    this.targetX = (int) Math.floor(this.xOffset + this.playerX);
                    this.targetY = (int) Math.floor(this.yOffset + this.playerY);
                    this.targetZ = (int) Math.floor(this.zOffset + this.playerZ);

                } while ((this.length <= this.range) && ((this.targetX == this.lastX) && (this.targetY == this.lastY) && (this.targetZ == this.lastZ)));
            }
        } else if (this.targetY < minWorldHeight) {
            while (this.targetY < minWorldHeight && this.length <= this.range) {
                this.lastX = this.targetX;
                this.lastY = this.targetY;
                this.lastZ = this.targetZ;

                do {
                    this.length += this.step;

                    this.hLength = (this.length * this.rotYCos);
                    this.yOffset = (this.length * this.rotYSin);
                    this.xOffset = (this.hLength * this.rotXCos);
                    this.zOffset = (this.hLength * this.rotXSin);

                    this.targetX = (int) Math.floor(this.xOffset + this.playerX);
                    this.targetY = (int) Math.floor(this.yOffset + this.playerY);
                    this.targetZ = (int) Math.floor(this.zOffset + this.playerZ);

                } while ((this.length <= this.range) && ((this.targetX == this.lastX) && (this.targetY == this.lastY) && (this.targetZ == this.lastZ)));
            }
        }
    }

    /**
     * Returns the current block along the line of vision.
     *
     * @return IBlock
     */
    public final IBlock getCurBlock() {
        if (this.length > this.range || this.targetY > maxWorldHeight || this.targetY < minWorldHeight) {
            return null;
        } else {
            return this.world.getBlock(this.targetX, this.targetY, this.targetZ);
        }
    }

    /**
     * Returns the block attached to the face at the cursor, or null if out of range.
     *
     * @return IBlock
     */
    public final IBlock getFaceBlock() {
        while ((this.getNextBlock() != null) && (this.getCurBlock().getMaterial() == new BukkitMaterial(Material.AIR))) {
        }

        if (this.getCurBlock() != null) {
            return this.getLastBlock();
        } else {
            return null;
        }
    }

    /**
     * Returns the previous block along the line of vision.
     *
     * @return IBlock
     */
    public final IBlock getLastBlock() {
        if (this.lastY > maxWorldHeight || this.lastY < minWorldHeight) {
            return null;
        }
        return this.world.getBlock(this.lastX, this.lastY, this.lastZ);
    }

    /**
     * Returns STEPS forward along line of vision and returns block.
     *
     * @return IBlock
     */
    public final IBlock getNextBlock() {
        this.lastX = this.targetX;
        this.lastY = this.targetY;
        this.lastZ = this.targetZ;
        do {
            this.length += this.step;

            this.hLength = (this.length * this.rotYCos);
            this.yOffset = (this.length * this.rotYSin);
            this.xOffset = (this.hLength * this.rotXCos);
            this.zOffset = (this.hLength * this.rotXSin);

            this.targetX = (int) Math.floor(this.xOffset + this.playerX);
            this.targetY = (int) Math.floor(this.yOffset + this.playerY);
            this.targetZ = (int) Math.floor(this.zOffset + this.playerZ);

        } while ((this.length <= this.range) && ((this.targetX == this.lastX) && (this.targetY == this.lastY) && (this.targetZ == this.lastZ)));

        if (this.length > this.range || this.targetY > maxWorldHeight || this.targetY < minWorldHeight) {
            return null;
        }

        return this.world.getBlock(this.targetX, this.targetY, this.targetZ);
    }

    /**
     * @return IBlock
     */
    public final IBlock getRangeBlock() {
        this.fromOffworld();
        if (this.length > this.range) {
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
        this.fromOffworld();
        while ((this.getNextBlock() != null) && (this.getCurBlock().getMaterial().getVoxelMaterial() == VoxelMaterial.AIR)) {

        }
        return this.getCurBlock();
    }

    private IBlock getRange() {
        this.lastX = this.targetX;
        this.lastY = this.targetY;
        this.lastZ = this.targetZ;

        do {
            this.length += this.step;

            this.hLength = (this.length * this.rotYCos);
            this.yOffset = (this.length * this.rotYSin);
            this.xOffset = (this.hLength * this.rotXCos);
            this.zOffset = (this.hLength * this.rotXSin);

            this.targetX = (int) Math.floor(this.xOffset + this.playerX);
            this.targetY = (int) Math.floor(this.yOffset + this.playerY);
            this.targetZ = (int) Math.floor(this.zOffset + this.playerZ);

        } while ((this.length <= this.range) && ((this.targetX == this.lastX) && (this.targetY == this.lastY) && (this.targetZ == this.lastZ)));

        if (this.world.getBlock(this.targetX, this.targetY, this.targetZ).getMaterial() != new BukkitMaterial(Material.AIR)) {
            return this.world.getBlock(this.targetX, this.targetY, this.targetZ);
        }

        if (this.length > this.range || this.targetY > maxWorldHeight || this.targetY < minWorldHeight) {
            return this.world.getBlock(this.lastX, this.lastY, this.lastZ);
        } else {
            return this.getRange();
        }
    }

    private void init(final ILocation location, final double range, final double step, final double viewHeight) {
        this.maxWorldHeight = world.getMaxWorldHeight();
        this.minWorldHeight = world.getMinWorldHeight();
        this.playerLoc = location;
        this.viewHeight = viewHeight;
        this.playerX = this.playerLoc.getX();
        this.playerY = this.playerLoc.getY() + this.viewHeight;
        this.playerZ = this.playerLoc.getZ();
        this.range = range;
        this.step = step;
        this.length = 0;
        this.rotX = (this.playerLoc.getYaw() + 90) % 360;
        this.rotY = this.playerLoc.getPitch() * -1;
        this.rotYCos = Math.cos(Math.toRadians(this.rotY));
        this.rotYSin = Math.sin(Math.toRadians(this.rotY));
        this.rotXCos = Math.cos(Math.toRadians(this.rotX));
        this.rotXSin = Math.sin(Math.toRadians(this.rotX));

        this.targetX = (int) Math.floor(this.playerLoc.getX());
        this.targetY = (int) Math.floor(this.playerLoc.getY() + this.viewHeight);
        this.targetZ = (int) Math.floor(this.playerLoc.getZ());
        this.lastX = this.targetX;
        this.lastY = this.targetY;
        this.lastZ = this.targetZ;
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
    public static void painting(final AbstractPlayer p, final boolean auto, final boolean back, final int choice) {
        ILocation targetLocation = p.getTargetBlock(null, 4).getLocation();
        IChunk paintingChunk = p.getTargetBlock(null, 4).getLocation().getChunk();
        double bestDistanceMatch = 50.0;
        Painting bestMatch = null;
        for (IEntity entity : paintingChunk.getEntities()) {
            if (((BukkitEntityType)entity.getType()).getType() == EntityType.PAINTING) {
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
                        p.sendMessage(ChatColor.RED + "This is the final painting, try scrolling to the other direction.");
                        return;
                    }
                    bestMatch.setArt(art);
                    p.sendMessage(ChatColor.GREEN + "Painting set to ID: " + (i));
                } catch (final Exception e) {
                    p.sendMessage(ChatColor.RED + "Oops. Something went wrong.");
                }
            } else {
                try {
                    Art art = Art.getById(choice);
                    bestMatch.setArt(art);
                    p.sendMessage(ChatColor.GREEN + "Painting set to ID: " + choice);
                } catch (final Exception exception) {
                    p.sendMessage(ChatColor.RED + "Your input was invalid somewhere.");
                }
            }
        }
    }
}
