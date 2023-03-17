package com.github.kevindagame.brush.polymorphic.property

import com.github.kevindagame.brush.perform.BasePerformer
import com.github.kevindagame.brush.perform.Performer
import com.github.kevindagame.brush.perform.pMaterial
import com.google.common.collect.ImmutableList

class PerformerProperty : PolyProperty<BasePerformer>("performer", "Set the performer", pMaterial(), aliases = ImmutableList.of("p")) {
    override fun set(value: String?) {
        val newPerfomer = Performer.getPerformer(value) ?: return
        this.value = newPerfomer
    }


    override fun getValues(): List<String> {
        return Performer.getPerformerHandles().toList()
    }
}