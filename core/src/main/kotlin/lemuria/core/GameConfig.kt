package lemuria.core

import lemuria.core.models.WorldDef
import lemuria.core.models.WorldMap

class GameConfig(
    worldConfig: DataReader = WorldConfig()
) {
    val world: WorldDef = loadJson<WorldDef>(worldConfig)
    var map: WorldMap? = null

    fun loadMap(fileName: String) {
        val reader = JsonFileReader(fileName)
        map = loadJson<WorldMap>(reader)
    }

    fun createWorld() { map = WorldMap(world) }
}
