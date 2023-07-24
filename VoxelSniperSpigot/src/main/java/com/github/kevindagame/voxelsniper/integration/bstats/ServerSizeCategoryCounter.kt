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

    /**
     * Gets the data for the server size category chart.
     * @param playerCount The amount of players online on the server.
     * @return The data for the server size category chart.
     * The data consists of a map with the label of the category, and a map with the exact value. The bstats docs do not explain the hardcoded 1. I assume this means that it's either 1 deep or a weight of 1
     */
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