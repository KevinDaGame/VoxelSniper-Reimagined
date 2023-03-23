package com.github.kevindagame.util


import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VersionCheckerTest {
    @Test
    fun testToString() {
        val version = Version(1, 2, 3)
        assertEquals("v1.2.3", version.toString())
    }

    @Test
    fun testCompareTo() {
        val v1 = Version(1, 0, 0)
        val v2 = Version(1, 0, 1)
        val v3 = Version(1, 1, 0)
        val v4 = Version(2, 0, 0)

        assertTrue(v1 < v2)
        assertTrue(v2 < v3)
        assertTrue(v3 < v4)

        assertEquals(0, v1.compareTo(Version(1, 0, 0)))
        assertTrue(v1 != v2)
        assertTrue(v2 != v3)
        assertTrue(v3 != v4)
    }

}

