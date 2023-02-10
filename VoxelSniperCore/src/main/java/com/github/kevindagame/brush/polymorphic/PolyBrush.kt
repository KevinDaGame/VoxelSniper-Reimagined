package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.perform.PerformerBrush
import com.github.kevindagame.brush.polymorphic.property.PolyPropertiesEnum
import com.github.kevindagame.brush.polymorphic.property.PolyProperty
import com.github.kevindagame.brush.polymorphic.property.SmoothProperty
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.Messages
import com.github.kevindagame.util.VoxelMessage
import com.github.kevindagame.voxelsniper.location.BaseLocation
import kotlin.math.pow

class PolyBrush(
    name: String,
    permissionNode: String,
    val shapes: MutableList<PolyBrushShape>,
    val operation: PolyOperationType,
) : PerformerBrush() {
    var properties: MutableList<PolyProperty<*>> = mutableListOf()

    init {
        this.name = name
        this.permissionNode = permissionNode
        val properties = mutableSetOf<PolyPropertiesEnum>()
        for (shape in shapes) {
            properties.addAll(shape.parameters.toList())
        }
        for (property in properties) {
            this.properties.add(property.supplier())
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
        val arguments = properties.map { it.name }.toMutableList()
        arguments.addAll(super.registerArguments())
        return arguments
    }

    override fun parseParameters(triggerHandle: String, params: Array<out String>, v: SnipeData) {
        super.parseParameters(triggerHandle, params, v)
        if (params[0].equals("info", ignoreCase = true)) {
            val info = Messages.POLY_BRUSH_USAGE.replace("brushName", name);
            for(property in properties) {
                info.append(Messages.POLY_BRUSH_USAGE_LINE.replace("brushHandle", triggerHandle).replace("parameter", property.name).replace("description", property.description).toString())
            }
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
        super.registerArgumentValues().forEach { (t, u) -> map.put(t, u) }
        return map
    }

    companion object {
        var SMOOTH_CIRCLE_VALUE = 0.5
        var VOXEL_CIRCLE_VALUE = 0.0
    }

}
