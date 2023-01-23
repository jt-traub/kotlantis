package lemuria.core

import kotlin.random.Random

// For now suppress unused paramater warnings
@Suppress("UNUSED_PARAMETER")
class GameInterface(val name: String, val version: String) {
    // Game information
    val title = name.replaceFirstChar { it.uppercase() }
    var config: GameConfig? = null

    // Random number instance this game will use
    var rng: Random = Random(2023)

    fun loadConfig(reader: DataReader? = null): GameInterface {
        val confReader = reader ?: WorldConfig()
        config = GameConfig(confReader)
        return this
    }

    fun newGame(): GameInterface {
        config!!.createWorld()
        return this
    }

    fun loadGame(reader: DataReader? = null): GameInterface {
        val dataReader = reader ?: GameData()
        config!!.loadData(dataReader)
        return this
    }

    fun saveGame(writer: DataWriter? = null): GameInterface {
        val dataWriter = writer ?: GameOutput()
        config!!.saveData(dataWriter)
        return this
    }

    fun generateStats(): GameInterface { return this }
    fun executeTurn(): GameInterface { return this }
    fun writePlayers(): GameInterface { return this }
    fun viewMap(mapType: String, _mapFile: String): GameInterface { return this }
    fun editGame(): GameInterface { return this }
    fun checkOrders(ordersName: String, checkName: String): GameInterface { return this }
    fun mapUnitsToFaction(): GameInterface { return this }
    fun generateRules(ruleIntroName: String, cssName: String, outputName: String): GameInterface { return this }
}
