package lemuria.core.commands

import lemuria.core.CommandLineExecutor
import lemuria.core.GameInterface

class NewGame : CommandLineExecutor {
    override val key = "new"
    override val usage = "new"
    override val description = "Create a brand new game"
    override val argCount = 0

    override fun invoke(game: GameInterface, vararg args: String) {
        checkArgs(*args)
        game.newGame()
            .saveGame()
            .writePlayers()
    }
}
