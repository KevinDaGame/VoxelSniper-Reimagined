package com.github.kevindagame.brush

import java.util.*
import java.util.function.Supplier

/**
 * Builder class for (old) brushes
 */
class BrushBuilder {
    private lateinit var name: String
    private lateinit var permission: String
    private val aliases: MutableList<String> = mutableListOf()
    private lateinit var supplier: Supplier<out IBrush>

    /**
     * Sets the name of the brush.
     */
    fun name(name: String): BrushBuilder
    {
        this.name = name
        return this
    }

    /**
     * Add aliases for the brush.
     */
    fun alias(vararg aliases: String): BrushBuilder
    {
        this.aliases.addAll(aliases)
        return this
    }

    /**
     * Sets the permission of the brush.
     */
    fun setPermission(permission: String): BrushBuilder
    {
        this.permission = permission
        return this
    }

    /**
     * Sets the class of the brush.
     */
    fun setSupplier(supplier: Supplier<out IBrush>): BrushBuilder
    {
        this.supplier = supplier
        return this
    }

    fun build(): BrushData {
        if(!::permission.isInitialized) {
            permission = "voxelsniper.brush.${name.lowercase(Locale.getDefault())}"
        }
        return BrushData(name, permission, aliases, supplier)
    }
}