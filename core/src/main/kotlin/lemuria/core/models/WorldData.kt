@file:UseSerializers(CoordinateSerializer::class)

package lemuria.core.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import lemuria.core.models.serde.CoordinateSerializer

@Serializable
data class GameRegion(
    val name: String,
    val coord: Pair<Int, Int>
)

@Serializable
data class GameUnit(
    val id: Int,
    val name: String
)

@Serializable
data class WorldLayer(
    val name: String,
    val type: String,
    val regions: MutableMap<Pair<Int, Int>, GameRegion> = mutableMapOf()
)

@Serializable
class WorldData {
    val layers = mutableMapOf<Int, WorldLayer>()
    val units = mutableListOf<GameUnit>()

    constructor(config: WorldDef) {
        config.setup.forEachIndexed { index, value ->
            val l = WorldLayer(value.name, value.type)
            layers[index] = l
            l.regions[Pair(index, index)] = GameRegion("test", Pair(index, index))
        }
        for (i in 0..3) {
            units.add(GameUnit(i, "test$i"))
        }
    }
}
