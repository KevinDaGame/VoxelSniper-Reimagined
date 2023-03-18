package com.github.kevindagame.brush.polymorphic

/**
 * A location in the brush radius
 * @param x The x coordinate relative to the brush center
 * @param y The y coordinate relative to the brush center
 * @param z The z coordinate relative to the brush center
 */
data class PolyLocation(val dx: Int, val dy: Int, val dz: Int)