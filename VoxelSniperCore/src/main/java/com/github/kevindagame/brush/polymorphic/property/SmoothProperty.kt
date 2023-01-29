package com.github.kevindagame.brush.polymorphic.property

class SmoothProperty : PolyProperty<Boolean>("smooth", "Toggle using smooth sphere algorithm (default: false)", false) {
    override fun set(value: String?) {
        this.value = value?.toBoolean() ?: !this.value
    }

    override fun getValues(): List<String> {
        return listOf("true", "false")
    }

}
