package lemuria.core

import lemuria.core.commands.CheckOrders
import lemuria.core.commands.EditGame
import lemuria.core.commands.GameStats
import lemuria.core.commands.GenerateMap
import lemuria.core.commands.GenerateRules
import lemuria.core.commands.MapUnits
import lemuria.core.commands.NewGame
import lemuria.core.commands.RunGame

interface CommandLineExecutor {
    val key: String
    val description: String
    val usage: String
    val argCount: Int

    fun invoke(game: GameInterface, vararg args: String)

    fun checkArgs(vararg args: String) {
        if (args.size != argCount) {
            throw CommandLineException("Incorrect number of args for command '$key': Expected $argCount, received: ${args.size}")
        }
    }
}

object CommandLineArgs {
    val commands: Map<String, CommandLineExecutor>
        get() = listOf(
            NewGame(),
            RunGame(),
            EditGame(),
            GameStats(),
            CheckOrders(),
            GenerateMap(),
            GenerateRules(),
            MapUnits()
        ).associate { it.key to it }
}
