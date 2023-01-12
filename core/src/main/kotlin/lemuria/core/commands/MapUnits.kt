package lemuria.core.commands

import lemuria.core.CommandLineExecutor
import lemuria.core.GameInterface

class MapUnits : CommandLineExecutor {
    override val key = "mapunits"
    override val usage = "mapunits"
    override val description = "Create a map from unit id to faction id"
    override val argCount = 0

    override fun invoke(game: GameInterface, vararg args: String) {
        checkArgs(*args)
        game.loadGame()
            .mapUnitsToFaction()
    }
}
