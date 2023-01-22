package lemuria.engine

import lemuria.core.BuildProperties
import lemuria.core.CommandLineArgs
import lemuria.core.CommandLineException
import lemuria.core.GameException
import lemuria.core.GameInterface
import kotlin.system.exitProcess

private fun usage(game: GameInterface) {
    CommandLineArgs.commands.forEach { command -> println("${game.name} ${command.value.usage}") }
}

fun main(args: Array<String>) {
    val buildProperties = BuildProperties()
    val progName = buildProperties.getProperty("progname")
    val version = buildProperties.getProperty("version")
    val game = GameInterface(progName, version)

    println("${game.title} Engine Version: ${game.version}")

    if (args.size < 1) {
        usage(game)
        exitProcess(1)
    }

    try {
        game.loadConfig()

        println("${game.config!!.world.rulesetTitle} Ruleset Version: ${game.config!!.world.version}\n")

        val commands = CommandLineArgs.commands
        if (args[0] in commands) {
            commands[args[0]]!!.invoke(game, *(args.drop(1).toTypedArray()))
        }
    } catch (e: GameException) {
        println("Error: ${e.message}")
        if (e is CommandLineException) {
            println("")
            usage(game)
        } else {
            val env = System.getenv("DEBUG")
            if (env != null && env.toInt() != 0) {
                println("")
                println("Detailed Error\n---------")
                e.printStackTrace()
            }
        }
        exitProcess(1)
    }

    exitProcess(0)
}
