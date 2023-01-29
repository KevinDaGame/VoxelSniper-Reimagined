package com.github.kevindagame.brush.polymorphic.property

abstract class PolyProperty<T>(val name: String, val description: String, default: T) {

    var value: T = default
    fun get(): T {
        return value
    }

    abstract fun set(value: String?)

    abstract fun getValues(): List<String>
}