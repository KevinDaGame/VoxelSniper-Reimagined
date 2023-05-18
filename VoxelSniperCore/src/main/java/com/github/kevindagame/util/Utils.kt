package com.github.kevindagame.util

import java.util.*

object Utils {
    fun <T> unmodifiableList(): List<T> {
        return Collections.unmodifiableList(ArrayList<T>())
    }

    fun <T> unmodifiableList(arg: T): List<T> {
        return Collections.unmodifiableList(newArrayList(arg))
    }

    @JvmStatic
    fun <T> newArrayList(vararg args: T): List<T> {
        val lst: MutableList<T> = ArrayList()
        lst.addAll(args)
        return lst
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
