package lemuria.core.models

import kotlinx.serialization.Serializable

@Serializable
data class Layer(
    val name: String,
    val type: String
)

@Serializable
class WorldMap {
    val layers = mutableMapOf<Int, Layer>()

    constructor(config: WorldDef) {
        config.setup.forEachIndexed { index, value ->
            val l = Layer(value.name, value.type)
            layers[index] = l
        }
    }
}
