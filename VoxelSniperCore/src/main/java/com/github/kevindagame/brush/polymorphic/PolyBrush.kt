package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.perform.PerformerBrush
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.Shapes
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.util.brushOperation.BlockOperation
import com.github.kevindagame.util.brushOperation.BrushOperation
import com.github.kevindagame.voxelsniper.location.BaseLocation
import kotlin.math.pow

class PolyBrush(
    name: String,
    permissionNode: String,
    val aliases: MutableList<String>,
    val shapes: MutableList<PolyBrushShape>,
    val operation: PolyOperation
) : PerformerBrush() {
    // TODO How to make this configurable?
    private val smooth: Boolean = false

    init {
        this.name = name
        this.permissionNode = permissionNode
    }

    override fun info(vm: VoxelMessage) {
        vm.brushName(name)
        vm.size()
    }

    override fun doArrow(v: SnipeData) {
        executeBrush(v)
    }

    override fun doPowder(v: SnipeData) {
        executeBrush(v)
    }


    fun executeBrush(v: SnipeData) {
        this.positions = getPositions(v)
    }

    private fun getPositions(v: SnipeData): List<BaseLocation> {
        val positions = initPositions(v)
        val newPositions = mutableListOf<BaseLocation>()
        val radiusSquared = (v.brushSize + if (smooth) SMOOTH_CIRCLE_VALUE else VOXEL_CIRCLE_VALUE).pow(2)
        val center = targetBlock

        for (position in positions) {
            for (shape in shapes) {
                if (shape.apply(v, position, radiusSquared)) {
                    newPositions.add(
                        BaseLocation(
                            world,
                            (center.x + position.dx).toDouble(),
                            (center.y + position.dy).toDouble(),
                            (center.z + position.dz).toDouble()
                        )
                    )
                }
            }
        }
        return newPositions
    }

    /**
     * Initialize the positions for every block in the brush radius in the shape of a cube
     * "A relative location a day keeps the Math pain away" - KevinDaGame
     */
    private fun initPositions(v: SnipeData): MutableList<PolyLocation> {
        var positions = mutableListOf<PolyLocation>()
        val brushSize = v.brushSize
        for (z in brushSize downTo -brushSize) {
            for (x in brushSize downTo -brushSize) {
                for (y in brushSize downTo -brushSize) {
                    positions.add(
                        PolyLocation(x, y, z)
                    )
                }
            }
        }
        return positions
    }

    companion object {
        var SMOOTH_CIRCLE_VALUE = 0.5
        var VOXEL_CIRCLE_VALUE = 0.0
    }

}
