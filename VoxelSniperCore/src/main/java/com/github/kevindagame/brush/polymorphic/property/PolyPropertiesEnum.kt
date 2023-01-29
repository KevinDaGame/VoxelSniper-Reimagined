package com.github.kevindagame.brush.polymorphic.property

import kotlin.reflect.KFunction0

enum class PolyPropertiesEnum(val supplier: KFunction0<SmoothProperty>) {
    SMOOTH(::SmoothProperty)
}