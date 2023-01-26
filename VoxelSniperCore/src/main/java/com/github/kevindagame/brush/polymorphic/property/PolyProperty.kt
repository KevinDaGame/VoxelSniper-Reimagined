package com.github.kevindagame.brush.polymorphic.property

abstract class PolyProperty<T>(default: T) {

    var value: T = default
    fun get(): T {
        return value
    }
}