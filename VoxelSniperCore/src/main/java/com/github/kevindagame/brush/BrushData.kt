package com.github.kevindagame.brush

import java.util.function.Supplier

open class BrushData(val name: String, val permission: String, val aliases: MutableList<String>, var supplier: Supplier<out IBrush>)
