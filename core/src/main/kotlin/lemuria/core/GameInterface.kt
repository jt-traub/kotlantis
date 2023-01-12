package lemuria.core

import kotlin.random.Random
// import kotlin.reflect.full.primaryConstructor

open class GameInterface(name: String, version: String, ruleset: String? = null, ruleVersion: String? = null) {
    // Game information
    val engineName = name
    val engineTitle = engineName.replaceFirstChar { it.uppercase() }
    val engineVersion = version
    val rulesetName = ruleset ?: "<ruleset>"
    val rulesetTitle = rulesetName.replaceFirstChar { it.uppercase() }
    val rulesetVersion = ruleVersion
    val rulesetValid = (ruleset != null)

    // Random number instance this game will use
    var rng: Random = Random(2023)

    // These functions can be overwritten in a ruleset
    open fun newGame(): GameInterface { return this }

    // These functions are not intended to be overwritten in a ruleset
    fun loadRuledata(): GameInterface { return this }
    fun loadGame(): GameInterface { return this }
    fun generateStats(): GameInterface { return this }
    fun executeTurn(): GameInterface { return this }
    fun saveGame(): GameInterface { return this }
    fun writePlayers(): GameInterface { return this }
    open /* for now */ fun viewMap(mapType: String, mapFile: String): GameInterface { return this }
    fun editGame(): GameInterface { return this }
    open /* for now */ fun checkOrders(ordersName: String, checkName: String): GameInterface { return this }
    fun mapUnitsToFaction(): GameInterface { return this }
    open /* for now */ fun generateRules(ruleIntroName: String, cssName: String, outputName: String): GameInterface { return this }

    // This is a way to dynamically load the correct game implementation (for customizing world creation, etc)
    /*
    companion object {
        fun factory(name: String, version: String, ruleset: String? = null): GameInterface {
            if (ruleset == null) {
                return GameInterface(name, version)
            } else {
                try {
                    val cls = Class.forName(ruleset + "." + "GameImpl").kotlin
                    val ctor = cls.primaryConstructor!!
                    return ctor.call(name, version) as GameInterface
                } catch (e: ClassNotFoundException) {
                    return GameInterface(name, version)
                }
            }
        }
    }
    */
}
