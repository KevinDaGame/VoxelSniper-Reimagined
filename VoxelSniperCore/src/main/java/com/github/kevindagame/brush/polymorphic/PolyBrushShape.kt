package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.polymorphic.property.PolyPropertiesEnum
import com.github.kevindagame.snipe.SnipeData
import kotlin.math.pow

enum class PolyBrushShape(vararg val parameters: PolyPropertiesEnum) : IPolyBrushShape {
    BALL(PolyPropertiesEnum.SMOOTH) {
        override fun apply(v: SnipeData, location: PolyLocation, radiusSquared: Double): Boolean {
            return (location.dx.toDouble().pow(2) + location.dy.toDouble().pow(2) + location.dz.toDouble()
                .pow(2) <= radiusSquared)
        }
    },
    CYLINDER(PolyPropertiesEnum.SMOOTH) {
        override fun apply(v: SnipeData, location: PolyLocation, radiusSquared: Double): Boolean {
            return (location.dx.toDouble().pow(2) + location.dz.toDouble().pow(2) <= radiusSquared)
        }
    },
    VOXEL() {
        override fun apply(v: SnipeData, location: PolyLocation, radiusSquared: Double): Boolean {
            return true
        }
    },
    DISC(PolyPropertiesEnum.SMOOTH) {
        override fun apply(v: SnipeData, location: PolyLocation, radiusSquared: Double): Boolean {
            return (location.dx.toDouble().pow(2) + 0 + location.dz.toDouble()
                .pow(2) <= radiusSquared) && location.dy == 0
        }
    },
    VOXEL_DISC() {
        override fun apply(v: SnipeData, location: PolyLocation, radiusSquared: Double): Boolean {
            return location.dy == 0
        }
    };
}

interface IPolyBrushShape {
    fun apply(v: SnipeData, location: PolyLocation, radiusSquared: Double): Boolean
}