package com.github.kevindagame.util

import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author KevinDaGame
 * A class that checks for the latest version of VoxelSniper-Reimagined on GitHub.
 */
class VersionChecker() {
    private val releasesApiUrl = "https://api.github.com/repos/$REPOSITORY_OWNER/$REPOSITORY_NAME/releases"

    /**
     * Gets the latest version info from the GitHub API.
     * @param currentVersion The current version of VoxelSniper-Reimagined.
     *
     * @return The latest version info, or null if the current version is the latest.
     */
    fun getLatestVersionInfo(currentVersion: String): VersionInfo? {
        val releases = getReleasesFromApi()
        if (releases.isEmpty()) {
            return null
        }
        var latestRelease: Release? = null

        for (release in releases) {
            val version = release.tag_name.lowercase().removePrefix("v")
            if (isOutdated(currentVersion, version) && (latestRelease == null || isOutdated(latestRelease.tag_name, version))) {
                latestRelease = release
            }
        }

        if (latestRelease != null) {
            val latestVersion = latestRelease.tag_name.lowercase().removePrefix("v")
            val downloadUrl = latestRelease.html_url
            LATEST_VERSION = VersionInfo(latestVersion, downloadUrl)
            return LATEST_VERSION
        }
        return null
    }

    /**
     * Gets the latest version info from the GitHub API.
     * @return The latest version info as a list of releases.
     */
    private fun getReleasesFromApi(): List<Release> {
        return try {
            val conn = URL(releasesApiUrl).openConnection() as HttpURLConnection
            conn.addRequestProperty("User-Agent", "Mozilla/5.0")
            val reader = InputStreamReader(conn.inputStream)
            val releases = Gson().fromJson(reader, Array<Release>::class.java)
            reader.close()
            conn.disconnect()
            releases.filterNot { it.prerelease }.toList()
        } catch (e: Exception) {
            System.err.println("Failed to check for a new version of VoxelSniper-Reimagined.")
            emptyList()
        }
    }

    /**
     * Checks if the current version is outdated.
     * @param currentVersion The current version of VoxelSniper-Reimagined.
     * @param latestVersion The latest version of VoxelSniper-Reimagined.
     *
     * @return True if the current version is outdated, false otherwise.
     */
    private fun isOutdated(currentVersion: String, latestVersion: String): Boolean {
        val current = parseVersion(currentVersion)
        val latest = parseVersion(latestVersion)
        return current < latest
    }

    /**
     * Parses a version string into a Version object.
     * @param version The version string.
     *
     * @return The parsed version.
     */
    private fun parseVersion(version: String): Version {
        val versionParts = version.removePrefix("v").split(".")
        val major = versionParts.getOrElse(0) { "0" }.toInt()
        val minor = versionParts.getOrElse(1) { "0" }.toInt()
        val patch = versionParts.getOrElse(2) { "0" }.toInt()
        return Version(major, minor, patch)
    }

    companion object {
        const val REPOSITORY_OWNER = "KevinDaGame"
        const val REPOSITORY_NAME = "VoxelSniper-Reimagined"
        var LATEST_VERSION: VersionInfo? = null
    }
}

/**
 * A data class that represents a version.
 */
data class Version(val major: Int, val minor: Int, val patch: Int) : Comparable<Version> {
    /**
     * Compares this version to another version.
     * @param other The other version.
     */
    override fun compareTo(other: Version): Int {
        if (major != other.major) {
            return major.compareTo(other.major)
        }
        if (minor != other.minor) {
            return minor.compareTo(other.minor)
        }
        return patch.compareTo(other.patch)
    }

    override fun toString(): String {
        return "v$major.$minor.$patch"
    }
}

data class Release(
    val html_url: String,
    val tag_name: String,
    val assets: List<Asset>,
    val prerelease: Boolean
)

data class Asset(
    val browser_download_url: String,
    val name: String
)

data class VersionInfo(val latestVersion: String, val downloadUrl: String)
