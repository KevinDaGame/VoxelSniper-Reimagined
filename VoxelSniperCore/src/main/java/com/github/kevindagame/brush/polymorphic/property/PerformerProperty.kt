package com.github.kevindagame.brush.polymorphic.property

import com.github.kevindagame.brush.perform.BasePerformer
import com.github.kevindagame.brush.perform.Performer
import com.github.kevindagame.brush.perform.pMaterial
import com.github.kevindagame.util.Utils

class PerformerProperty : PolyProperty<BasePerformer>("performer", "Set the performer", pMaterial(), aliases = Utils.unmodifiableList("P")) {
    override fun set(value: String?) {
        val newPerfomer = Performer.getPerformer(value) ?: return
        this.value = newPerfomer
    }


    override fun getValues(): List<String> {
        return Performer.getPerformerHandles().toList()
    }

    override fun getAsString(): String {
        return value.name
    }
}