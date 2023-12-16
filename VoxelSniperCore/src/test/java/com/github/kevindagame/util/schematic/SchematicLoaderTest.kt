package com.github.kevindagame.util.schematic

import org.junit.Test
import org.mockito.Mockito
import java.io.File

class SchematicLoaderTest {

    @Test
    fun gatherSchematics_match_empty_folder_throw_IllegalArgumentException() {
        // Arrange
        val folder = File("empty_folder")
        val schematicReaderMock = Mockito.mock(ISchematicReader::class.java)
        Mockito.`when`(schematicReaderMock.getSchematicFolder("empty_folder")).thenReturn(folder)
        Mockito.`when`(schematicReaderMock.readSchematicFolder(folder)).thenReturn(emptyList())

        val schematicLoader = VoxelSchematicLoader(schematicReaderMock)

        val name = "empty_folder"

        // Act
        val exception = try {
            schematicLoader.gatherSchematics(name)
            null
        } catch (e: IllegalArgumentException) {
            e
        }

        // Assert
        assert(exception != null)
        assert(exception!!.message!!.contains(name))
    }

    @Test
    fun gatherSchematics_match_non_empty_folder_returns_Schematics() {
        // Arrange
        val folder = File("empty_folder")
        val schematicInFolder = VoxelSchematic(listOf(), "Fancy tree")
        val schematicReaderMock = Mockito.mock(ISchematicReader::class.java)
        Mockito.`when`(schematicReaderMock.getSchematicFolder("empty_folder")).thenReturn(folder)
        Mockito.`when`(schematicReaderMock.readSchematicFolder(folder)).thenReturn(listOf(schematicInFolder))

        val schematicLoader = VoxelSchematicLoader(schematicReaderMock)
        val name = "empty_folder"

        // Act
        val schematics = schematicLoader.gatherSchematics(name)

        // Assert
        assert(schematics.size == 1)
        assert(schematics[0] == schematicInFolder)
    }

    @Test
    fun gatherSchematics_match_file_returns_Schematic() {
        // Arrange
        val schematicFile = File("fancy_tree.schem")
        val schematic = VoxelSchematic(listOf(), "Fancy tree")
        val schematicReaderMock = Mockito.mock(ISchematicReader::class.java)

        Mockito.`when`(schematicReaderMock.getSchematicFolder("fancy_tree")).thenReturn(null)
        Mockito.`when`(schematicReaderMock.getSchematicFile("fancy_tree")).thenReturn(schematicFile)
        Mockito.`when`(schematicReaderMock.readSchematicFile(schematicFile)).thenReturn(schematic)

        val schematicLoader = VoxelSchematicLoader(schematicReaderMock)
        val name = "fancy_tree"

        // Act
        val schematics = schematicLoader.gatherSchematics(name)

        // Assert
        assert(schematics.size == 1)
        assert(schematics[0] == schematic)
    }

    @Test
    fun gatherSchematics_match_file_but_null_throws_IllegalArgumentException() {
        // Arrange
        val schematicFile = File("fancy_tree.schem")
        val schematicReaderMock = Mockito.mock(ISchematicReader::class.java)

        Mockito.`when`(schematicReaderMock.getSchematicFolder("fancy_tree")).thenReturn(null)
        Mockito.`when`(schematicReaderMock.getSchematicFile("fancy_tree")).thenReturn(schematicFile)
        Mockito.`when`(schematicReaderMock.readSchematicFile(schematicFile)).thenReturn(null)

        val schematicLoader = VoxelSchematicLoader(schematicReaderMock)
        val name = "fancy_tree"

        // Act
        val exception = try {
            schematicLoader.gatherSchematics(name)
            null
        } catch (e: IllegalArgumentException) {
            e
        }

        // Assert
        assert(exception != null)
        assert(exception!!.message!!.contains(name))
    }

    @Test
    fun gatherSchematics_no_match_throws_IllegalArgumentException() {
        // Arrange
        val schematicReaderMock = Mockito.mock(ISchematicReader::class.java)

        Mockito.`when`(schematicReaderMock.getSchematicFolder("fancy_tree")).thenReturn(null)
        Mockito.`when`(schematicReaderMock.getSchematicFile("fancy_tree")).thenReturn(null)

        val schematicLoader = VoxelSchematicLoader(schematicReaderMock)
        val name = "fancy_tree"

        // Act
        val exception = try {
            schematicLoader.gatherSchematics(name)
            null
        } catch (e: IllegalArgumentException) {
            e
        }

        // Assert
        assert(exception != null)
        assert(exception!!.message!!.contains(name))
    }

    @Test
    fun `getSchematicNamesForAutoComplete should return list of schematic names`() {
        // Arrange
        val expectedSchematicNames = listOf("schematic1", "schematic2", "schematic3")

        val schematicReader = Mockito.mock(ISchematicReader::class.java)
        Mockito.`when`(schematicReader.getPossibleSchematicNames()).thenReturn(expectedSchematicNames)
        val schematicLoader = VoxelSchematicLoader(schematicReader)

        //Act
        val schematicNames = schematicLoader.getSchematicNamesForAutoComplete()

        // Assert
        assert(schematicNames == expectedSchematicNames)
    }

}