package com.github.kevindagame.voxelsniper.integration.bstats

object ServerSizeCategoryCounter {
    private val categories = listOf(
        ServerSizeCategory("0", 0, 0),
        ServerSizeCategory("1-10", 1, 10),
        ServerSizeCategory("11-20", 11, 20),
        ServerSizeCategory("21-50", 21, 50),
        ServerSizeCategory("51-100", 51, 100),
        ServerSizeCategory("101-200", 101, 200),
        ServerSizeCategory("201-500", 201, 500),
        ServerSizeCategory("501-1000", 501, 1000),
        ServerSizeCategory("More than 1000", 1001, Int.MAX_VALUE),


    )
    fun getData(playerCount: Int): Map<String, Map<String, Int>> {
        val map = mutableMapOf<String, Map<String, Int>>()
        val entries = mutableMapOf<String, Int>()
        val category = categories.find { playerCount in it.min..it.max }
        entries[playerCount.toString()] = 1
        map[category!!.label] = entries

        return map
    }
}

data class ServerSizeCategory(val label: String, val min: Int, val max: Int)