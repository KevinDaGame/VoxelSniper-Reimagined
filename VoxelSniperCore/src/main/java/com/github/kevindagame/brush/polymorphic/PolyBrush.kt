package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.perform.PerformerBrush
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.util.VoxelMessage

class PolyBrush(name: String, permissionNode: String, val aliases: MutableList<String>, shapes: MutableList<PolyBrushShape>, operation: PolyOperation): PerformerBrush() {
init {
    this.name = name
    this.permissionNode = permissionNode
}
    override fun info(vm: VoxelMessage) {
        vm.brushName(name)
        vm.size()
    }

    override fun doArrow(v: SnipeData?) {
        TODO("Not yet implemented")
    }

    override fun doPowder(v: SnipeData?) {
        TODO("Not yet implemented")
    }

}