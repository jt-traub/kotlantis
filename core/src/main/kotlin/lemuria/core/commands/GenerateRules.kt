package lemuria.core.commands

import lemuria.core.CommandLineExecutor
import lemuria.core.GameInterface

class GenerateRules : CommandLineExecutor {
    override val key = "genrules"
    override val usage = "genrules <introfile> <cssfile> <outputfile>"
    override val description = "Generate an HTML rules file from the game config"
    override val argCount = 3

    override fun invoke(game: GameInterface, vararg args: String) {
        checkArgs(*args)
        game.generateRules(args[0], args[1], args[2])
    }
}
