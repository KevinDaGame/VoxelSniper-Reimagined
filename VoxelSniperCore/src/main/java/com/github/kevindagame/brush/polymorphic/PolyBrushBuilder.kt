package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.brush.polymorphic.operation.PolyOperation

class PolyBrushBuilder {
    private lateinit var name: String
    private lateinit var permission: String
    private var operation: PolyOperation? = null
    private var operationType: PolyOperationType = PolyOperationType.BLOCK
    private val aliases: MutableList<String> = mutableListOf()
    private val shapes: MutableList<PolyBrushShape> = mutableListOf()

    /**
     * Sets the name of the brush.
     */
    fun name(name: String): PolyBrushBuilder {
        this.name = name
        return this
    }

    /**
     * Add aliases for the brush.
     */
    fun alias(vararg aliases: String): PolyBrushBuilder {
        this.aliases.addAll(aliases)
        return this
    }

    /**
     * Sets the permission of the brush.
     */
    fun permission(permission: String): PolyBrushBuilder {
        this.permission = permission
        return this
    }

    /**
     * Add an operation to the brush.
     */
    fun shape(shape: PolyBrushShape): PolyBrushBuilder {
        shapes.add(shape)
        return this
    }

    /**
     * Set the operationType type of the brush By default this is {PolyOperationType.BLOCK}
     */
    fun operationType(operationType: PolyOperationType): PolyBrushBuilder {
        this.operationType = operationType
        return this
    }

    /**
     * Set an operation to apply to the brush area
     */
    fun operation(operation: PolyOperation): PolyBrushBuilder {
        this.operation = operation
        return this
    }

    /**
     * Build the brush
     */
    fun build(): PolyBrushData {
        return PolyBrushData(
            name,
            permission, aliases,
            { PolyBrush(name, permission, shapes, operationType, operation) },
            shapes,
            operationType
        )
    }
}