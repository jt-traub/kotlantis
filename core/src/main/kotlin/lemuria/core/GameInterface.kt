package lemuria.core

import kotlin.random.Random
// import kotlin.reflect.full.primaryConstructor

// For now suppress unused paramater warnings
@Suppress("UNUSED_PARAMETER")
class GameInterface(val name: String, val version: String) {
    // Game information
    val title = name.replaceFirstChar { it.uppercase() }
    var config: GameConfig? = null

    // Random number instance this game will use
    var rng: Random = Random(2023)

    fun loadConfig(): GameInterface {
        config = GameConfig()
        return this
    }

    fun newGame(): GameInterface { config!!.createWorld(); return this }
    fun loadGame(): GameInterface { config!!.loadMap("world.in"); return this }
    fun saveGame(): GameInterface { return this }

    fun generateStats(): GameInterface { return this }
    fun executeTurn(): GameInterface { return this }
    fun writePlayers(): GameInterface { return this }
    fun viewMap(mapType: String, _mapFile: String): GameInterface { return this }
    fun editGame(): GameInterface { return this }
    fun checkOrders(ordersName: String, checkName: String): GameInterface { return this }
    fun mapUnitsToFaction(): GameInterface { return this }
    fun generateRules(ruleIntroName: String, cssName: String, outputName: String): GameInterface { return this }
}
