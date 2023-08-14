package com.github.kevindagame.util

import java.util.*

object Utils {
    @JvmStatic
    fun <T> unmodifiableList(): List<T> {
        return listOf()
    }

    @JvmStatic
    fun <T> unmodifiableList(arg: T): List<T> {
        return listOf(arg)
    }

    @JvmStatic
    fun <T> unmodifiableList(vararg args: T): List<T> {
        return listOf(*args)
    }

    @JvmStatic
    fun <T> newArrayList(vararg args: T): List<T> {
        return mutableListOf(*args)
    }

    @JvmStatic
    fun <T> newArrayList(lst: Collection<T>): List<T> {
        return ArrayList(lst);
    }

    @JvmStatic
    fun <T> hashCode(vararg args: Int): Int {
        return args.contentHashCode();
    }
}
