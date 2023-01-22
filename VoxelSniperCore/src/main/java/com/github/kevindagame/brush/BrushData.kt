package com.github.kevindagame.brush

open class BrushData(val name: String, val permission: String, val aliases: MutableList<String>, var clazz: Class<out IBrush>)
