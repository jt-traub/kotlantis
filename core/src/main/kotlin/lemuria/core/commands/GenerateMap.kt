package lemuria.core.commands

import lemuria.core.CommandLineException
import lemuria.core.CommandLineExecutor
import lemuria.core.GameInterface

class GenerateMap : CommandLineExecutor {
    override val key = "map"
    override val usage = "map <maptype> <mapoutput>"
    override val description = "Create a map of the current game"
    override val argCount = 2

    override fun invoke(game: GameInterface, vararg args: String) {
        checkArgs(*args)
        game.loadGame()
            .viewMap(args[0], args[1])
    }

    override fun checkArgs(vararg args: String) {
        super.checkArgs(*args)
        if (!(args[0] in mapTypes)) {
            throw CommandLineException("Invalid value: '${args[0]}' is not a valid map type")
        }
    }

    companion object {
        val mapTypes = arrayOf("geo", "wmon", "lair", "gate")
    }
}
