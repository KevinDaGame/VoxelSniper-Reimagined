package com.github.kevindagame.brush.polymorphic.property

import com.google.common.collect.ImmutableList

abstract class PolyProperty<T>(val name: String, val description: String, default: T, val aliases: ImmutableList<String> = ImmutableList.of()) {

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