package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.BrushData
import com.github.kevindagame.brush.IBrush

class PolyBrushData(name: String, permission: String, aliases: MutableList<String>, clazz: Class<out IBrush>, val shapes: MutableList<PolyBrushShape>? , val operation: PolyOperation): BrushData(name, permission, aliases, clazz)
