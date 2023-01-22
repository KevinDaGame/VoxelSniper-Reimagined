package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.IBrush

class PolyBrushBuilder {
    private lateinit var name: String
    private lateinit var permission: String
    private lateinit var operation: PolyOperation

    private val aliases: MutableList<String> = mutableListOf()
    private val shapes: MutableList<PolyBrushShape> = mutableListOf()

    /**
     * Sets the name of the brush.
     */
    fun name(name: String): PolyBrushBuilder
    {
        this.name = name
        return this
    }

    /**
     * Add an alias for the brush.
     */
    fun alias(alias: String): PolyBrushBuilder
    {
        aliases.add(alias)
        return this
    }

    /**
     * Sets the permission of the brush.
     */
    fun permission(permission: String): PolyBrushBuilder
    {
        this.permission = permission
        return this
    }

    /**
     * Add an operation to the brush.
     */
    fun shape(shape: PolyBrushShape): PolyBrushBuilder
    {
        shapes.add(shape)
        return this
    }

    /**
     * Set the operation type of the brush
     */
    fun operation(operation: PolyOperation): PolyBrushBuilder
    {
        this.operation = operation
        return this
    }

    /**
     * Build the brush
     */
    fun build(): PolyBrushData {
        return PolyBrushData(name, permission, aliases, { PolyBrush(name, permission, aliases, shapes, operation) }, shapes, operation)
    }
}