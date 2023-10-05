package com.github.kevindagame.mocks

import com.github.kevindagame.voxelsniper.fileHandler.IVoxelSniperConfiguration
import com.github.kevindagame.voxelsniper.fileHandler.VoxelSniperConfiguration

class VoxelSniperConfigurationMock: IVoxelSniperConfiguration {
    override fun getUndoCacheSize(): Int {
        return 0
    }

    override fun setUndoCacheSize(size: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaxBrushSize(): Int {
        return 20
    }

    override fun isMessageOnLoginEnabled(): Boolean {
        return false
    }

    override fun setMessageOnLoginEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getDefaultBrush(): String {
        return "snipe"
    }

    override fun isPlotSquaredIntegrationEnabled(): Boolean {
        return false
    }

    override fun isWorldGuardIntegrationEnabled(): Boolean {
        return false
    }

    override fun isUpdateCheckerEnabled(): Boolean {
        return false
    }
}