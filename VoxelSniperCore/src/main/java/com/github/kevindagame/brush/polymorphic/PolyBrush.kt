package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.perform.PerformerBrush
import com.github.kevindagame.brush.polymorphic.property.PolyPropertiesEnum
import com.github.kevindagame.brush.polymorphic.property.PolyProperty
import com.github.kevindagame.brush.polymorphic.property.SmoothProperty
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.voxelsniper.location.BaseLocation
import kotlin.math.pow

class PolyBrush(
    name: String,
    permissionNode: String,
    val aliases: MutableList<String>,
    val shapes: MutableList<PolyBrushShape>,
    val operation: PolyOperation,
) : PerformerBrush() {
    var properties: MutableList<PolyProperty<*>> = mutableListOf()

    init {
        this.name = name
        this.permissionNode = permissionNode
        var properties = mutableSetOf<PolyPropertiesEnum>()
        for (shape in shapes) {
            properties.addAll(shape.parameters.toList())
        }
        for (property in properties) {
            this.properties.add(property.clazz.java.newInstance())
        }
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
        val radiusSquared = (v.brushSize + if (getSmooth()) SMOOTH_CIRCLE_VALUE else VOXEL_CIRCLE_VALUE).pow(2)
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
        val positions = mutableListOf<PolyLocation>()
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

    private fun getSmooth(): Boolean {
        for (property in properties) {
            if (property is SmoothProperty) {
                return property.value
            }
        }
        return false
    }

    override fun registerArguments(): List<String> {
        return properties.map { it.name }
    }

    override fun parseParameters(triggerHandle: String, params: Array<out String>, v: SnipeData) {
        if (params[0].equals("info", ignoreCase = true)) {
            TODO("Not yet implemented")
        }
        for (property in properties) {
            if (params[0].equals(property.name, ignoreCase = true)) {
                property.set(params[1])
                break

            }
        }
    }

    override fun registerArgumentValues(): HashMap<String, List<String>> {
        val map = HashMap<String, List<String>>()
        for(property in properties) {
            map.put(property.name, property.getValues())
        }
        return map
    }

    companion object {
        var SMOOTH_CIRCLE_VALUE = 0.5
        var VOXEL_CIRCLE_VALUE = 0.0
    }

}
