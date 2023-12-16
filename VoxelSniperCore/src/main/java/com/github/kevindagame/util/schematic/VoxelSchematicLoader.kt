package com.github.kevindagame.util.schematic

class VoxelSchematicLoader(private val schematicReader: ISchematicReader) {
    /**
     * Reads a schematic from a file or a folder.
     * check if there is a folder with the name.
     *     If there is a folder with that name, then it will read all schematics in that folder.
     *     If there are no schematics in that folder, then it will throw an IllegalArgumentException.
     * If there is no folder with that name, then it will check if there is a file with the <name>.schem.
     * If there is a file with the <name>.schem, then it will read the file.
     * If there is no file with the <name>.schem, then it will throw an IllegalArgumentException.
     *
     * @param name The name of the schematic or the folder.
     * @return A list of schematics.
     * @throws IllegalArgumentException If there is no file with the <name>.schem or no folder with that name.
     */
    fun gatherSchematics(name: String): List<VoxelSchematic> {
        val schematicFolder = schematicReader.getSchematicFolder(name)
        if (schematicFolder != null) {
            val schematics = schematicReader.readSchematicFolder(schematicFolder)
            if (schematics.isEmpty()) {
                throw IllegalArgumentException("Folder $name does not contain any schematics")
            }
            return schematics
        }

        val schematicFile = schematicReader.getSchematicFile(name)
        if (schematicFile != null) {
            val schematic = schematicReader.readSchematicFile(schematicFile)
                ?: throw IllegalArgumentException("Schematic $name is not valid")

            return listOf(schematic)
        }

        throw IllegalArgumentException("No schematic file or folder with name $name")
    }

    fun getSchematicNamesForAutoComplete(): List<String> {
       return schematicReader.getPossibleSchematicNames()
    }
}