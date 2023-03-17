package com.github.kevindagame.util

import com.github.kevindagame.voxelsniper.location.VoxelLocation
import com.github.kevindagame.voxelsniper.world.IWorld

enum class RotationAxis(val locationTranslator: Function4<IWorld, Int, Int, Int, VoxelLocation>) {
    X({world: IWorld, w: Int, h: Int, d: Int -> VoxelLocation(world, h, w, d) }),
    Y({world: IWorld, w: Int, h: Int, d: Int -> VoxelLocation(world, w, h, d) }),
    Z({world: IWorld, w: Int, h: Int, d: Int -> VoxelLocation(world, w, d, h) })


}