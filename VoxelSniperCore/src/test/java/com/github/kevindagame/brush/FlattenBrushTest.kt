package com.github.kevindagame.brush

import com.github.kevindagame.mocks.MockVoxelSniper
import com.github.kevindagame.mocks.VoxelPlayerMock
import com.github.kevindagame.mocks.VoxelWorldMock
import com.github.kevindagame.mocks.WorldType
import com.github.kevindagame.snipe.SnipeAction
import com.github.kevindagame.snipe.SnipeData
import com.github.kevindagame.snipe.Sniper
import org.junit.BeforeClass
import org.junit.Test

class FlattenBrushTest {
    @Test
    fun flatten() {
        //Arrange
        val world = VoxelWorldMock(WorldType.FLAT)
        val flattenBrush = FlattenBrush()
        val player = VoxelPlayerMock()
        val sniper = Sniper(player)
        val snipeData = SnipeData(sniper)

        val targetBlock = world.getBlock(200, 64, 200)
        val previousblock = world.getBlock(200, 65, 200)
        //Act
        flattenBrush.perform(SnipeAction.ARROW, snipeData, targetBlock, previousblock)

        //Assert


    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun setup(): Unit {
            MockVoxelSniper()
        }
    }
}