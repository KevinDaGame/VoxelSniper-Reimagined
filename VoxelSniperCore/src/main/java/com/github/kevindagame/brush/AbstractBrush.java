package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeAction;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.BlockHelper;
import com.github.kevindagame.util.BlockWrapper;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.location.VoxelLocation;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniper.world.IWorld;

import java.util.*;


/**
 * Abstract implementation of the {@link IBrush} interface.
 */
public abstract class AbstractBrush implements IBrush {

    protected static final int CHUNK_SIZE = 16;
    /**
     * Targeted Block.
     */
    private IBlock targetBlock;
    /**
     * Last Block before targeted Block.
     */
    private IBlock lastBlock;

    /**
     * The blocks this brush impacts
     */
    protected Set<VoxelLocation> positions = new HashSet<>();

    /**
     * Brush name.
     */
    private String name = "Undefined";
    protected boolean cancelled = false;
    protected SnipeAction snipeAction;

    private boolean preparePerform(final SnipeData v, final IBlock clickedBlock, final BlockFace clickedFace) {
        if (this.getTarget(v, clickedBlock, clickedFace)) {
            if (this instanceof PerformerBrush) {
                ((PerformerBrush) this).initP(v);
            }
            return true;
        }

        return false;
    }



    @Override
    public boolean perform(SnipeAction action, SnipeData data, IBlock targetBlock, IBlock lastBlock) {
        this.positions.clear();
        this.cancelled = false;
        this.setTargetBlock(targetBlock);
        this.setLastBlock(lastBlock);
        this.snipeAction = action;
        switch (action) {
            case ARROW:
                this.arrow(data);
                break;
            case GUNPOWDER:
                this.powder(data);
                break;
            default:
                return false;
        }
        if(!cancelled) {

        //TODO Create event
        return actPerform(data);
        }
        return false;

    }

    protected abstract boolean actPerform(SnipeData v);

    /**
     * The arrow action. Executed when a player RightClicks with an Arrow
     *
     * @param v Sniper caller
     */
    protected void arrow(final SnipeData v) {
    }

    /**
     * The powder action. Executed when a player RightClicks with Gunpowder
     *
     * @param v Sniper caller
     */
    protected void powder(final SnipeData v) {
    }

    @Override
    public abstract void info(VoxelMessage vm);

    @Override
    public void parseParameters(String triggerHandle, final String[] params, final SnipeData v) {
        v.sendMessage(Messages.BRUSH_NO_PARAMS_ACCEPTED);
    }

    // TODO: make abstract
    @Override
    public List<String> registerArguments() {
        // Return empty list if not overridden; i.e. no arguments to add.
        return new ArrayList<>();
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        // Do nothing because not all brushes have arguments that have values
        return new HashMap<>();
    }

    /**
     * Overridable getTarget method.
     *
     * @param v
     * @param clickedBlock
     * @param clickedFace
     * @return boolean
     */
    protected final boolean getTarget(final SnipeData v, final IBlock clickedBlock, final BlockFace clickedFace) {
        if (clickedBlock != null) {
            this.setTargetBlock(clickedBlock);
            this.setLastBlock(clickedBlock.getRelative(clickedFace));
            if (this.getLastBlock() == null) {
                v.sendMessage(Messages.TARGET_MUST_BE_VISIBLE);
                return false;
            }
            if (v.owner().getSnipeData(v.owner().getCurrentToolId()).isLightningEnabled()) {
                this.getWorld().strikeLightning(this.getTargetBlock().getLocation());
            }
            return true;
        } else {
            BlockHelper rangeBlockHelper;
            if (v.owner().getSnipeData(v.owner().getCurrentToolId()).isRanged()) {
                rangeBlockHelper = new BlockHelper(v.owner().getPlayer(), v.owner().getSnipeData(v.owner().getCurrentToolId()).getRange());
                this.setTargetBlock(rangeBlockHelper.getRangeBlock());
            } else {
                rangeBlockHelper = new BlockHelper(v.owner().getPlayer());
                this.setTargetBlock(rangeBlockHelper.getTargetBlock());
            }
            if (this.getTargetBlock() != null) {
                this.setLastBlock(rangeBlockHelper.getLastBlock());
                if (this.getLastBlock() == null) {
                    v.sendMessage(Messages.TARGET_MUST_BE_VISIBLE);
                    return false;
                }
                if (v.owner().getSnipeData(v.owner().getCurrentToolId()).isLightningEnabled()) {
                    this.getWorld().strikeLightning(this.getTargetBlock().getLocation());
                }
                return true;
            } else {
                v.sendMessage(Messages.TARGET_MUST_BE_VISIBLE);
                return false;
            }
        }
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getBrushCategory() {
        return "General";
    }

    /**
     * @return the targetBlock
     */
    protected final IBlock getTargetBlock() {
        return this.targetBlock;
    }

    /**
     * @param targetBlock the targetBlock to set
     */
    protected final void setTargetBlock(final IBlock targetBlock) {
        this.targetBlock = targetBlock;
    }

    /**
     * @return the world
     */
    protected final IWorld getWorld() {
        return targetBlock.getWorld();
    }

    protected final int getMinHeight() {
        return getWorld().getMinWorldHeight();
    }

    protected final int getMaxHeight() {
        return getWorld().getMaxWorldHeight();
    }

    protected final boolean isInWorldHeight(int height) {
        return getMinHeight() < height && getMaxHeight() > height;
    }

    /**
     * Looks up Type ID of Block at given coordinates in the world of the targeted Block.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return Type ID of Block at given coordinates in the world of the targeted Block.
     */
    protected VoxelMaterial getBlockMaterialAt(int x, int y, int z) {
        return isInWorldHeight(y) ? this.getWorld().getBlock(x, y, z).getMaterial() : VoxelMaterial.AIR;
    }

    /**
     * Looks up Block Data Value of Block at given coordinates in the world of the targeted Block.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return Block Data Value of Block at given coordinates in the world of the targeted Block.
     */
    protected IBlockData getBlockDataAt(int x, int y, int z) {
        return isInWorldHeight(y) ? this.getWorld().getBlock(x, y, z).getBlockData() : VoxelMaterial.AIR.createBlockData();
    }

    /**
     * @return Block before target Block.
     */
    protected final IBlock getLastBlock() {
        return this.lastBlock;
    }

    /**
     * @param lastBlock Last Block before target Block.
     */
    protected final void setLastBlock(IBlock lastBlock) {
        this.lastBlock = lastBlock;
    }

    /**
     * Sets the VoxelMaterial of the block at the passed coordinate. This function will automatically create use the default BlockData for that Material.
     *
     * @param x        X coordinate
     * @param y        Y coordinate
     * @param z        Z coordinate
     * @param material the material to set this block to
     */
    protected final void setBlockMaterialAt(int x, int y, int z, VoxelMaterial material) {
        if(isInWorldHeight(y)) {
            this.getWorld().getBlock(x, y, z).setBlockData(material.createBlockData());
        }
    }

    /**
     * Sets the BlockData value of the block at the passed coordinate. Will use the exact BlockData that is passed into the function and NOT the default
     * BlockData of the Material.
     *
     * @param x         X coordinate
     * @param y         Y coordinate
     * @param z         Z coordinate
     * @param blockData The blockData to set this block to
     */
    protected final void setBlockMaterialAndDataAt(int x, int y, int z, IBlockData blockData) {
        if(isInWorldHeight(y)) {
            this.getWorld().getBlock(x, y, z).setBlockData(blockData, true);
        }
    }

    /**
     * Sets the type of the passed block, and appends the block to the Undo container. This function will automatically create use the default BlockData for the passed Material.
     *
     * @param x        X coordinate
     * @param y        Y coordinate
     * @param z        Z coordinate
     * @param material the material to set this block to
     * @param undo     The Undo container to store the change
     */
    @Deprecated
    protected final void setBlockMaterialAt(int x, int y, int z, VoxelMaterial material, Undo undo) {
        if(isInWorldHeight(y)) {
            var b = getWorld().getBlock(x ,y ,z);
            if (b.getMaterial() != material) {
                undo.put(b);
            }
            b.setMaterial(material);
        }
    }

    /**
     * Sets the type of the passed block, and adds the block to the Undo container. This function will automatically create use the default BlockData for the passed Material.
     *
     * @param b        The block to change
     * @param material the material to set this block to
     * @param undo     The Undo container to store the change
     */
    protected final void setBlockType(IBlock b, VoxelMaterial material, Undo undo) {
        if(isInWorldHeight(b.getY())) {
            if (b.getMaterial() != material) {
                undo.put(b);
            }
            b.setMaterial(material);
        }

    }

    protected void cancel() {
        this.cancelled = true;
    }
}
