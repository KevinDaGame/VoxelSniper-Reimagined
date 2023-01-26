package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.BrushData
import com.github.kevindagame.brush.IBrush
import java.util.function.Supplier

class PolyBrushData(
    name: String,
    permission: String,
    aliases: MutableList<String>,
    supplier: Supplier<out IBrush>,
    val shapes: MutableList<PolyBrushShape>?,
    val operation: PolyOperation
) : BrushData(name, permission, aliases, supplier)
