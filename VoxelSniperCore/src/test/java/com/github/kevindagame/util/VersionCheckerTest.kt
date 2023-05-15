package com.github.kevindagame.util


import com.github.kevindagame.service.GithubService
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

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

    @Test
    fun getLatestVersionInfo_invalid_version() {
        val githubService = mock(GithubService::class.java)
        `when`(githubService.getReleasesFromApi()).thenReturn(listOf(Release("", "v1.0.0", emptyList<Asset>(), false)))
        val versionChecker = VersionChecker(githubService)
        val latestVersionInfo = versionChecker.getLatestVersionInfo("v1.1.0-SNAPSHOT")
        assertEquals(null, latestVersionInfo)
    }

}

