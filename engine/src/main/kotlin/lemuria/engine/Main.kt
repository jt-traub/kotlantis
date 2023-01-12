package lemuria.engine

import lemuria.core.BuildProperties
import lemuria.core.CommandLineArgs
import lemuria.core.CommandLineException
import lemuria.core.GameException
import lemuria.ruleset.GameImpl
import kotlin.system.exitProcess

private fun usage(game: GameImpl) {
    CommandLineArgs.commands.forEach { command ->
        println("${game.engineName} ${command.value.usage}")
    }
}

fun main(args: Array<String>) {
    val buildProperties = BuildProperties()
    val progName = buildProperties.getProperty("progname")
    val version = buildProperties.getProperty("version")
    val game = GameImpl(progName, version)

    println("${game.engineTitle} Engine Version: ${game.engineVersion}")
    println("${game.rulesetTitle} Version: ${game.rulesetVersion}\n")

    if (args.size < 1) {
        usage(game)
        exitProcess(1)
    }

    try {
        game.loadRuledata()

        val commands = CommandLineArgs.commands
        if (args[0] in commands) {
            commands[args[0]]!!.invoke(game, *(args.drop(1).toTypedArray()))
        }
    } catch (e: GameException) {
        println("Error: ${e.message}")
        if (e is CommandLineException) {
            println("")
            usage(game)
        }
        exitProcess(1)
    }

    exitProcess(0)
}
