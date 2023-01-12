package lemuria.core.commands

import lemuria.core.CommandLineExecutor
import lemuria.core.GameInterface

class GameStats : CommandLineExecutor {
    override val key = "stats"
    override val usage = "stats"
    override val description = "Show game statistics"
    override val argCount = 0

    override fun invoke(game: GameInterface, vararg args: String) {
        checkArgs(*args)
        game.loadGame()
            .generateStats()
    }
}
