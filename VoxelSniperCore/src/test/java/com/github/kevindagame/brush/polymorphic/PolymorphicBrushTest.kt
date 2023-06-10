package com.github.kevindagame.brush.polymorphic

import com.github.kevindagame.VoxelSniper
import com.github.kevindagame.brush.polymorphic.operation.BlendOperation
import com.github.kevindagame.brush.polymorphic.property.*
import com.github.kevindagame.voxelsniper.IVoxelsniper
import com.github.kevindagame.voxelsniper.biome.VoxelBiome
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class PolymorphicBrushTest {


    @Before
    fun setup() {
        val main = Mockito.mock(IVoxelsniper::class.java)
        Mockito.`when`<VoxelBiome>(main.getBiome("minecraft", "plains")).thenReturn(VoxelBiome("minecraft", "plains"))
        VoxelSniper.voxelsniper = main
    }

    @Test
    fun ball_brush_should_have_performer_and_smooth() {
        //arrange
        val brush = PolyBrush(
            "Test",
            "test",
            mutableListOf(PolyBrushShape.BALL),
            PolyOperationType.BLOCK,
            null
        )

        //act
        val hasPerformers = brush.properties.any { it is PerformerProperty }
        val hasSmooth = brush.properties.any { it is SmoothProperty }

        //assert
        assert(hasPerformers)
        assert(hasSmooth)
    }

    @Test
    fun biome_should_not_have_performer_or_smooth_but_biome_property() {
        //arrange
        val brush = PolyBrush(
            "Test",
            "test",
            mutableListOf(),
            PolyOperationType.BIOME,
            null
        )

        //act
        val hasPerformers = brush.properties.any { it is PerformerProperty }
        val hasBiome = brush.properties.any { it is BiomeProperty }
        val hasSmoothProperty = brush.properties.any { it is SmoothProperty }

        //assert
        assert(!hasPerformers)
        assert(hasBiome)
        assert(!hasSmoothProperty)
    }

   @Test
   fun operation_brush_should_have_exclude_water_and_exclude_air() {
       //arrange
       val brush = PolyBrush(
           "Test",
           "test",
           mutableListOf(),
           PolyOperationType.BLOCK,
           BlendOperation()
       )

       //act
       val hasExcludeWater = brush.properties.any { it is ExcludeWaterProperty }
       val hasExcludeAir = brush.properties.any { it is ExcludeAirProperty }

       //assert
       assert(hasExcludeWater)
       assert(hasExcludeAir)
   }

}