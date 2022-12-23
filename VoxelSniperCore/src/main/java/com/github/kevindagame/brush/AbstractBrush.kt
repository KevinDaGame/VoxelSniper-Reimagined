package com.github.kevindagame.brush

import com.github.kevindagame.util.BrushOperation.BrushOperation
import com.github.kevindagame.brush.perform.PerformerBrush
import com.github.kevindagame.snipe.SnipeAction
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.util.BlockHelper
import com.github.kevindagame.util.BrushOperation.CustomOperation
import com.github.kevindagame.util.Messages
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.voxelsniper.block.BlockFace
import com.github.kevindagame.voxelsniper.block.IBlock
import com.github.kevindagame.voxelsniper.blockdata.IBlockData
import com.github.kevindagame.voxelsniper.events.player.PlayerSnipeEvent
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import com.github.kevindagame.voxelsniper.world.IWorld
import com.google.common.collect.ImmutableList
import java.util.*

/**
 * Abstract implementation of the [IBrush] interface.
 */
abstract class AbstractBrush : IBrush {
    /**
     * @return the targetBlock
     */
    /**
     * @param targetBlock the targetBlock to set
     */
    /**
     * Targeted Block.
     */
    protected lateinit var targetBlock: IBlock;
    /**
     * @return Block before target Block.
     */
    /**
     * @param lastBlock Last Block before target Block.
     */
    /**
     * Last Block before targeted Block.
     */
    protected lateinit var lastBlock: IBlock;

    /**
     * The operations this brush performs
     */
    private val operations: MutableList<BrushOperation> = ArrayList()

    /**
     * Brush name.
     */
    private var name = "Undefined"
    protected var cancelled = false
    protected var snipeAction: SnipeAction? = null

    private fun preparePerform(v: SnipeData, clickedBlock: IBlock, clickedFace: BlockFace): Boolean {
        if (getTarget(v, clickedBlock, clickedFace)) {
            if (this is PerformerBrush) {
                this.initP(v)
            }
            return true
        }
        return false
    }

    protected fun addOperation(operation: BrushOperation) {
        operations.add(operation)
    }

    protected fun addOperations(operations: Collection<BrushOperation>) {
        this.operations.addAll(operations)
    }
    override fun perform(action: SnipeAction, data: SnipeData, targetBlock: IBlock, lastBlock: IBlock): Boolean {
        operations.clear()
        this.targetBlock = targetBlock
        this.lastBlock = lastBlock
        snipeAction = action
        when (action) {
            SnipeAction.ARROW -> arrow(data)
            SnipeAction.GUNPOWDER -> powder(data)
        }
        return performOperations(data)
    }

    protected fun performOperations(data: SnipeData): Boolean {
        if (operations.size == 0) return false
        val event = PlayerSnipeEvent(data.owner().player, this, ImmutableList.copyOf(operations)).callEvent()
        if (!event.isCancelled && event.operations.size > 0) {
                val undo = Undo()
            if(event.isCustom) {
                val cb = this as CustomBrush
                cb.perform(event.operations as ImmutableList<CustomOperation>, data, undo)
            }
            var reloadArea = false
            for (operation in event.operations) {
                if(!operation.isCancelled) {
                reloadArea = reloadArea || executeOperation(operation, undo)

                }
            }
            if (reloadArea) {
                reloadBrushArea(data)
            }
        }
        return false
    }

    private fun reloadBrushArea(v: SnipeData) {
        val brushSize = v.brushSize
        val block1 = world.getBlock(targetBlock!!.x - brushSize, 0, targetBlock!!.z - brushSize)
        val block2 = world.getBlock(targetBlock!!.x + brushSize, 0, targetBlock!!.z + brushSize)
        val lowChunkX = if (block1.x <= block2.x) block1.chunk.x else block2.chunk.x
        val lowChunkZ = if (block1.z <= block2.z) block1.chunk.z else block2.chunk.z
        val highChunkX = if (block1.x >= block2.x) block1.chunk.x else block2.chunk.x
        val highChunkZ = if (block1.z >= block2.z) block1.chunk.z else block2.chunk.z
        for (x in lowChunkX..highChunkX) {
            for (z in lowChunkZ..highChunkZ) {
                world.refreshChunk(x, z)
            }
        }
    }

    /**
     *
     * @param operation
     * @param undo
     * @return whether to reload the area
     */
    private fun executeOperation(operation: BrushOperation, undo: Undo): Boolean {
        operation.perform(undo)
        return false
    }

    /**
     * The arrow action. Executed when a player RightClicks with an Arrow
     *
     * @param v Sniper caller
     */
    protected open fun arrow(v: SnipeData?) {}

    /**
     * The powder action. Executed when a player RightClicks with Gunpowder
     *
     * @param v Sniper caller
     */
    protected open fun powder(v: SnipeData?) {}
    abstract override fun info(vm: VoxelMessage)
    override fun parseParameters(triggerHandle: String, params: Array<String>, v: SnipeData) {
        v.sendMessage(Messages.BRUSH_NO_PARAMS_ACCEPTED)
    }

    override fun registerArguments(): List<String> {
        // Return empty list if not overridden; i.e. no arguments to add.
        return ArrayList()
    }

    override fun registerArgumentValues(): HashMap<String, List<String>> {
        // Do nothing because not all brushes have arguments that have values
        return HashMap()
    }

    /**
     * Overridable getTarget method.
     *
     * @param v
     * @param clickedBlock
     * @param clickedFace
     * @return boolean
     */
    protected fun getTarget(v: SnipeData, clickedBlock: IBlock?, clickedFace: BlockFace?): Boolean {
        return if (clickedBlock != null) {
            targetBlock = clickedBlock
            lastBlock = clickedBlock.getRelative(clickedFace)
            if (lastBlock == null) {
                v.sendMessage(Messages.TARGET_MUST_BE_VISIBLE)
                return false
            }
            if (v.owner().getSnipeData(v.owner().currentToolId).isLightningEnabled) {
                world.strikeLightning(targetBlock!!.location)
            }
            true
        } else {
            val rangeBlockHelper: BlockHelper
            if (v.owner().getSnipeData(v.owner().currentToolId).isRanged) {
                rangeBlockHelper = BlockHelper(
                    v.owner().player, v.owner().getSnipeData(v.owner().currentToolId).range
                        .toDouble()
                )
                targetBlock = rangeBlockHelper.rangeBlock
            } else {
                rangeBlockHelper = BlockHelper(v.owner().player)
                targetBlock = rangeBlockHelper.targetBlock
            }
            if (targetBlock != null) {
                lastBlock = rangeBlockHelper.lastBlock
                if (lastBlock == null) {
                    v.sendMessage(Messages.TARGET_MUST_BE_VISIBLE)
                    return false
                }
                if (v.owner().getSnipeData(v.owner().currentToolId).isLightningEnabled) {
                    world.strikeLightning(targetBlock!!.location)
                }
                true
            } else {
                v.sendMessage(Messages.TARGET_MUST_BE_VISIBLE)
                false
            }
        }
    }

    override fun getName(): String {
        return name
    }

    override fun setName(name: String) {
        this.name = name
    }

    override fun getBrushCategory(): String {
        return "General"
    }

    /**
     * @return the world
     */
    protected val world: IWorld
        protected get() = targetBlock!!.world
    protected val minHeight: Int
        protected get() = world.minWorldHeight
    protected val maxHeight: Int
        protected get() = world.maxWorldHeight

    protected fun isInWorldHeight(height: Int): Boolean {
        return minHeight <= height && maxHeight > height
    }

    /**
     * Looks up Type ID of Block at given coordinates in the world of the targeted Block.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return Type ID of Block at given coordinates in the world of the targeted Block.
     */
    protected fun getBlockMaterialAt(x: Int, y: Int, z: Int): VoxelMaterial {
        return if (isInWorldHeight(y)) world.getBlock(x, y, z).material else VoxelMaterial.AIR
    }

    /**
     * Looks up Block Data Value of Block at given coordinates in the world of the targeted Block.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return Block Data Value of Block at given coordinates in the world of the targeted Block.
     */
    protected fun getBlockDataAt(x: Int, y: Int, z: Int): IBlockData {
        return if (isInWorldHeight(y)) world.getBlock(x, y, z).blockData else VoxelMaterial.AIR.createBlockData()
    }

    /**
     * Sets the VoxelMaterial of the block at the passed coordinate. This function will automatically create use the default BlockData for that Material.
     *
     * @param x        X coordinate
     * @param y        Y coordinate
     * @param z        Z coordinate
     * @param material the material to set this block to
     */
    protected fun setBlockMaterialAt(x: Int, y: Int, z: Int, material: VoxelMaterial) {
        if (isInWorldHeight(y)) {
            world.getBlock(x, y, z).blockData = material.createBlockData()
        }
    }

    protected fun cancel() {
        cancelled = true
    }

    companion object {
        protected const val CHUNK_SIZE = 16
    }
}