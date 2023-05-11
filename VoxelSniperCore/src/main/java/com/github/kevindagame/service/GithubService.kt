package com.github.kevindagame.service

import com.github.kevindagame.util.Release
import com.github.kevindagame.util.VersionChecker
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GithubService {
    private val releasesApiUrl = "https://api.github.com/repos/${VersionChecker.REPOSITORY_OWNER}/${VersionChecker.REPOSITORY_NAME}/releases"

    /**
     * Gets the latest version info from the GitHub API.
     * @return The latest version info as a list of releases.
     */
    fun getReleasesFromApi(): List<Release> {
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
}