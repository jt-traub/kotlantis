package lemuria.core.commands

import lemuria.core.CommandLineExecutor
import lemuria.core.GameInterface

class RunGame : CommandLineExecutor {
    override val key = "run"
    override val usage = "run"
    override val description = "Run the game turn"
    override val argCount = 0

    override fun invoke(game: GameInterface, vararg args: String) {
        checkArgs(*args)
        game.loadGame()
            .executeTurn()
            .saveGame()
    }
}
