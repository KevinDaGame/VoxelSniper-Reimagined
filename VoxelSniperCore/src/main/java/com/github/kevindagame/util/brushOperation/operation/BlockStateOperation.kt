package com.github.kevindagame.util.brushOperation.operation

import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.voxelsniper.blockstate.IBlockState
import com.github.kevindagame.voxelsniper.location.BaseLocation

class BlockStateOperation(location: BaseLocation, val oldState: IBlockState, var newState: IBlockState) :
    BrushOperation(location) {
    private var force = false
    private var applyPhysics = true

    constructor(location: BaseLocation, oldState: IBlockState, newState: IBlockState, force: Boolean) : this(
        location, oldState, newState
    ) {
        this.force = force
    }

    constructor(
        location: BaseLocation,
        oldState: IBlockState,
        newState: IBlockState,
        force: Boolean,
        applyPhysics: Boolean
    ) : this(
        location, oldState, newState, force
    ) {
        this.applyPhysics = applyPhysics
    }

    override fun perform(undo: Undo): Boolean {
        undo.put(oldState)
        return newState.update(force, applyPhysics)
    }
}