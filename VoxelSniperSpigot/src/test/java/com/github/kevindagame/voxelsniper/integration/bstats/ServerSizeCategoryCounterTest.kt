package com.github.kevindagame.voxelsniper.integration.bstats

import org.junit.Assert
import org.junit.Test

class ServerSizeCategoryCounterTest {

    @Test
    fun testGetData() {
        val testData = listOf(
            0 to "0",
            5 to "1-10",
            15 to "11-20",
            30 to "21-50",
            75 to "51-100",
            150 to "101-200",
            300 to "201-500",
            750 to "501-1000",
            2000 to "More than 1000"
        )

        for ((playerCount, expectedCategory) in testData) {
            val result = ServerSizeCategoryCounter.getData(playerCount)
            val categoryMap = result[expectedCategory]
            Assert.assertEquals(1, categoryMap?.get(playerCount.toString()))
        }
    }
}