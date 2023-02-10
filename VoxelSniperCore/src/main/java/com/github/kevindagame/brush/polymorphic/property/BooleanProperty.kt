package com.github.kevindagame.brush.polymorphic.property

open class BooleanProperty(name: String, description: String, default: Boolean): PolyProperty<Boolean>(name, description, default) {
    override fun set(value: String?) {
        this.value = value?.toBoolean() ?: !this.value
    }

    override fun getValues(): List<String> {
        return listOf("true", "false")
    }
}