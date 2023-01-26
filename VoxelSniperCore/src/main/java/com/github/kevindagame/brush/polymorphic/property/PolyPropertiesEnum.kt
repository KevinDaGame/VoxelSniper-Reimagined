package com.github.kevindagame.brush.polymorphic.property

import kotlin.reflect.KClass

enum class PolyPropertiesEnum(val clazz: KClass<out PolyProperty<*>>) {
    SMOOTH(SmoothProperty::class)
}