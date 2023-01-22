package lemuria.core.models

import kotlinx.serialization.Serializable

@Serializable
data class LayerDef(
    val name: String,
    val type: String
)

@Serializable
data class WorldDef(
    val ruleset: String,
    val version: String,
    val world: String,
    val flags: WorldFlags,
    val setup: Array<LayerDef>
) {
    val rulesetTitle = ruleset.replaceFirstChar { it.uppercase() }
}

@Serializable
enum class WorldFlag {
    Flag1,
    Flag2,
    Flag3
}

typealias WorldFlags = Set<WorldFlag>
