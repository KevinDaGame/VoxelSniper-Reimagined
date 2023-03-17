package com.github.kevindagame.brush

import com.github.kevindagame.snipe.SnipeAction
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.voxelsniper.block.IBlock
import util.InitOnceProperty
import kotlin.properties.ReadWriteProperty

inline fun <reified T> initOnce(): ReadWriteProperty<Any, T> = InitOnceProperty()

/**
 * Brush Interface.
 */
interface IBrush {
    /**
     * @param vm Message object
     */
    fun info(vm: VoxelMessage)

    /**
     * Handles parameters passed to brushes.
     *
     * @param triggerHandle the handle that triggered this brush
     * @param params        Array of string containing parameters
     * @param v             Snipe Data
     */
    fun parseParameters(triggerHandle: String, params: Array<String>, v: SnipeData)
    fun perform(action: SnipeAction, data: SnipeData, targetBlock: IBlock, lastBlock: IBlock): Boolean
    /**
     * @return The name of the Brush
     */
    /**
     * @param name New name for the Brush
     */
    var name: String

    /**
     * @return Permission node required to use this brush
     */
    var permissionNode: String

    /**
     * Registers the additional arguments for the tab completion
     *
     * @return
     */
    fun registerArguments(): List<String>

    /**
     * Registers the additional arguments for the tab completion
     *
     * @return
     */
    fun registerArgumentValues(): HashMap<String, List<String>>
}