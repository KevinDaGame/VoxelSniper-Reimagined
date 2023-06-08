package com.github.kevindagame.util;

import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.entity.IEntity;
import com.github.kevindagame.voxelsniper.location.BaseLocation;
import com.github.kevindagame.voxelsniper.vector.VoxelVector;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class performs ray tracing and iterates along blocks on a line
 * Based on the BlockIterator class from Bukkit
 */

public class BlockIterator implements Iterator<IBlock> {

    private final IWorld world;
    private final int maxDistance;

    private static final int gridSize = 1 << 24;

    private boolean end = false;

    private IBlock[] blockQueue = new IBlock[3];
    private int currentBlock = 0;
    private int currentDistance = 0;
    private int maxDistanceInt;

    private int secondError;
    private int thirdError;

    private int secondStep;
    private int thirdStep;

    private BlockFace mainFace;
    private BlockFace secondFace;
    private BlockFace thirdFace;

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param world       The world to use for tracing
     * @param start       A Vector giving the initial BaseLocation for the trace
     * @param direction   A Vector pointing in the direction for the trace
     * @param yOffset     The trace begins vertically offset from the start vector
     *                    by this value
     * @param maxDistance This is the maximum distance in blocks for the
     *                    trace. Setting this value above 140 may lead to problems with
     *                    unloaded chunks. A value of 0 indicates no limit
     */
    public BlockIterator(IWorld world, VoxelVector start, VoxelVector direction, double yOffset, int maxDistance) {
        this.world = world;
        this.maxDistance = maxDistance;

        VoxelVector startClone = start.clone();

        startClone.setY(startClone.getY() + yOffset);

        currentDistance = 0;

        double mainDirection = 0;
        double secondDirection = 0;
        double thirdDirection = 0;

        double mainPosition = 0;
        double secondPosition = 0;
        double thirdPosition = 0;

        IBlock startBlock = this.world.getBlock(startClone.getLocation(world));

        if (getXLength(direction) > mainDirection) {
            mainFace = getXFace(direction);
            mainDirection = getXLength(direction);
            mainPosition = getXPosition(direction, startClone, startBlock);

            secondFace = getYFace(direction);
            secondDirection = getYLength(direction);
            secondPosition = getYPosition(direction, startClone, startBlock);

            thirdFace = getZFace(direction);
            thirdDirection = getZLength(direction);
            thirdPosition = getZPosition(direction, startClone, startBlock);
        }
        if (getYLength(direction) > mainDirection) {
            mainFace = getYFace(direction);
            mainDirection = getYLength(direction);
            mainPosition = getYPosition(direction, startClone, startBlock);

            secondFace = getZFace(direction);
            secondDirection = getZLength(direction);
            secondPosition = getZPosition(direction, startClone, startBlock);

            thirdFace = getXFace(direction);
            thirdDirection = getXLength(direction);
            thirdPosition = getXPosition(direction, startClone, startBlock);
        }
        if (getZLength(direction) > mainDirection) {
            mainFace = getZFace(direction);
            mainDirection = getZLength(direction);
            mainPosition = getZPosition(direction, startClone, startBlock);

            secondFace = getXFace(direction);
            secondDirection = getXLength(direction);
            secondPosition = getXPosition(direction, startClone, startBlock);

            thirdFace = getYFace(direction);
            thirdDirection = getYLength(direction);
            thirdPosition = getYPosition(direction, startClone, startBlock);
        }

        // trace line backwards to find intercept with plane perpendicular to the main axis

        double d = mainPosition / mainDirection; // how far to hit face behind
        double secondd = secondPosition - secondDirection * d;
        double thirdd = thirdPosition - thirdDirection * d;

        // Guarantee that the ray will pass though the start block.
        // It is possible that it would miss due to rounding
        // This should only move the ray by 1 grid position
        secondError = ((int) Math.floor(secondd * gridSize));
        secondStep = ((int) Math.round(secondDirection / mainDirection * gridSize));
        thirdError = ((int) Math.floor(thirdd * gridSize));
        thirdStep = ((int) Math.round(thirdDirection / mainDirection * gridSize));

        if (secondError + secondStep <= 0) {
            secondError = -secondStep + 1;
        }

        if (thirdError + thirdStep <= 0) {
            thirdError = -thirdStep + 1;
        }

        IBlock lastBlock;

        lastBlock = startBlock.getRelative(mainFace.opposite());

        if (secondError < 0) {
            secondError += gridSize;
            lastBlock = lastBlock.getRelative(secondFace.opposite());
        }

        if (thirdError < 0) {
            thirdError += gridSize;
            lastBlock = lastBlock.getRelative(thirdFace.opposite());
        }

        // This means that when the variables are positive, it means that the coord=1 boundary has been crossed
        secondError -= gridSize;
        thirdError -= gridSize;

        blockQueue[0] = lastBlock;
        currentBlock = -1;

        scan();

        boolean startBlockFound = false;

        for (int cnt = currentBlock; cnt >= 0; cnt--) {
            if (blockEquals(blockQueue[cnt], startBlock)) {
                currentBlock = cnt;
                startBlockFound = true;
                break;
            }
        }

        if (!startBlockFound) {
            throw new IllegalStateException("Start block missed in BlockIterator");
        }

        // Calculate the number of planes passed to give max distance
        maxDistanceInt = ((int) Math.round(maxDistance / (Math.sqrt(mainDirection * mainDirection + secondDirection * secondDirection + thirdDirection * thirdDirection) / mainDirection)));

    }

    private boolean blockEquals(IBlock a, IBlock b) {
        return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
    }

    private BlockFace getXFace(VoxelVector direction) {
        return ((direction.getX() > 0) ? BlockFace.EAST : BlockFace.WEST);
    }

    private BlockFace getYFace(VoxelVector direction) {
        return ((direction.getY() > 0) ? BlockFace.UP : BlockFace.DOWN);
    }

    private BlockFace getZFace(VoxelVector direction) {
        return ((direction.getZ() > 0) ? BlockFace.SOUTH : BlockFace.NORTH);
    }

    private double getXLength(VoxelVector direction) {
        return Math.abs(direction.getX());
    }

    private double getYLength(VoxelVector direction) {
        return Math.abs(direction.getY());
    }

    private double getZLength(VoxelVector direction) {
        return Math.abs(direction.getZ());
    }

    private double getPosition(double direction, double position, int blockPosition) {
        return direction > 0 ? (position - blockPosition) : (blockPosition + 1 - position);
    }

    private double getXPosition(VoxelVector direction, VoxelVector position, IBlock block) {
        return getPosition(direction.getX(), position.getX(), block.getX());
    }

    private double getYPosition(VoxelVector direction, VoxelVector position, IBlock block) {
        return getPosition(direction.getY(), position.getY(), block.getY());
    }

    private double getZPosition(VoxelVector direction, VoxelVector position, IBlock block) {
        return getPosition(direction.getZ(), position.getZ(), block.getZ());
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param loc         The location for the start of the ray trace
     * @param yOffset     The trace begins vertically offset from the start VoxelVector
     *                    by this value
     * @param maxDistance This is the maximum distance in blocks for the
     *                    trace. Setting this value above 140 may lead to problems with
     *                    unloaded chunks. A value of 0 indicates no limit
     */
    public BlockIterator(BaseLocation loc, double yOffset, int maxDistance) {
        this(loc.getWorld(), loc.toVector(), loc.getDirection(), yOffset, maxDistance);
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param loc     The BaseLocation for the start of the ray trace
     * @param yOffset The trace begins vertically offset from the start VoxelVector
     *                by this value
     */

    public BlockIterator(BaseLocation loc, double yOffset) {
        this(loc.getWorld(), loc.toVector(), loc.getDirection(), yOffset, 0);
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param loc The BaseLocation for the start of the ray trace
     */

    public BlockIterator(BaseLocation loc) {
        this(loc, 0D);
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param entity      Information from the entity is used to set up the trace
     * @param maxDistance This is the maximum distance in blocks for the
     *                    trace. Setting this value above 140 may lead to problems with
     *                    unloaded chunks. A value of 0 indicates no limit
     */

    public BlockIterator(IEntity entity, int maxDistance) {
        this(entity.getLocation(), entity.getEyeLocation().getY(), maxDistance);
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param entity Information from the entity is used to set up the trace
     */

    public BlockIterator(IEntity entity) {
        this(entity, 0);
    }

    /**
     * Returns true if the iteration has more elements
     */

    @Override
    public boolean hasNext() {
        scan();
        return currentBlock != -1;
    }

    /**
     * Returns the next Block in the trace
     *
     * @return the next Block in the trace
     */
    @Override
    public IBlock next() throws NoSuchElementException {
        scan();
        if (currentBlock <= -1) {
            throw new NoSuchElementException();
        } else {
            return blockQueue[currentBlock--];
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("[BlockIterator] doesn't support block removal");
    }

    private void scan() {
        if (currentBlock >= 0) {
            return;
        }
        if (maxDistance != 0 && currentDistance > maxDistanceInt) {
            end = true;
            return;
        }
        if (end) {
            return;
        }

        currentDistance++;

        secondError += secondStep;
        thirdError += thirdStep;

        if (secondError > 0 && thirdError > 0) {
            blockQueue[2] = blockQueue[0].getRelative(mainFace);
            if (((long) secondStep) * ((long) thirdError) < ((long) thirdStep) * ((long) secondError)) {
                blockQueue[1] = blockQueue[2].getRelative(secondFace);
                blockQueue[0] = blockQueue[1].getRelative(thirdFace);
            } else {
                blockQueue[1] = blockQueue[2].getRelative(thirdFace);
                blockQueue[0] = blockQueue[1].getRelative(secondFace);
            }
            thirdError -= gridSize;
            secondError -= gridSize;
            currentBlock = 2;
            return;
        } else if (secondError > 0) {
            blockQueue[1] = blockQueue[0].getRelative(mainFace);
            blockQueue[0] = blockQueue[1].getRelative(secondFace);
            secondError -= gridSize;
            currentBlock = 1;
            return;
        } else if (thirdError > 0) {
            blockQueue[1] = blockQueue[0].getRelative(mainFace);
            blockQueue[0] = blockQueue[1].getRelative(thirdFace);
            thirdError -= gridSize;
            currentBlock = 1;
            return;
        } else {
            blockQueue[0] = blockQueue[0].getRelative(mainFace);
            currentBlock = 0;
            return;
        }
    }
}
