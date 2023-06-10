package com.github.kevindagame.voxelsniper.biome

import com.github.kevindagame.IKeyed
import com.github.kevindagame.VoxelSniper

@JvmRecord
data class VoxelBiome(val namespace: String, val key: String) : IKeyed {
    override fun getNameSpace(): String {
        return namespace
    }

    override fun getKey(): String {
        return key
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val (namespace1, key1) = o as VoxelBiome
        return if (namespace != namespace1) false else key == key1
    }

    override fun hashCode(): Int {
        var result = namespace.hashCode()
        result = 31 * result + key.hashCode()
        return result
    }

    public fun getName(): String {
      return "$namespace:$key"
    }

    override fun toString(): String {
        return "$namespace:$key"
    }

    companion object {
        @JvmStatic
        fun PLAINS(): VoxelBiome {
            return VoxelBiome("minecraft", "plains") // We can safely assume that this biome will always exist, so no need to check for null
        }

        @JvmStatic
        fun getBiome(key: String): VoxelBiome? {
            if (key.contains(":")) {
                val split = key.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                return getBiome(split[0], split[1])
            }
            return getBiome("minecraft", key)
        }

        @JvmStatic
        fun getBiome(namespace: String?, key: String?): VoxelBiome? {
            return VoxelSniper.voxelsniper.getBiome(namespace, key)
        }

        @JvmStatic
        val biomes: List<VoxelBiome>
            get() = VoxelSniper.voxelsniper.biomes
    }
}
