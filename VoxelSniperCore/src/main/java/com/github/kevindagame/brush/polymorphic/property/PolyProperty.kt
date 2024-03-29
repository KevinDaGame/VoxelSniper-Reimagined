package com.github.kevindagame.brush.polymorphic.property

abstract class PolyProperty<T>(val name: String, val description: String, val default: T, val aliases: List<String> = listOf()) {

    var value: T = default
    fun get(): T {
        return value
    }

    abstract fun set(value: String?)

    abstract fun getValues(): List<String>
    open fun getAsString(): String {
        return value.toString()
    }
}