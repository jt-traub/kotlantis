package lemuria.core

import lemuria.core.models.WorldData
import lemuria.core.models.WorldDef

class GameConfig(
    worldConfig: DataReader
) {
    val world: WorldDef = loadJson<WorldDef>(worldConfig)
    var data: WorldData? = null

    fun loadData(reader: DataReader) {
        data = loadJson<WorldData>(reader)
    }

    fun createWorld() { data = WorldData(world) }

    fun saveData(writer: DataWriter) {
        saveJson(data, writer)
    }
}
