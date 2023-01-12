package lemuria.core.commands

import lemuria.core.CommandLineExecutor
import lemuria.core.GameInterface

class CheckOrders : CommandLineExecutor {
    override val key = "check"
    override val usage = "check <orderfile> <outputfile>"
    override val description = "Checks a set of orders for syntax"
    override val argCount = 2

    override fun invoke(game: GameInterface, vararg args: String) {
        checkArgs(*args)
        game.checkOrders(args[0], args[1])
    }
}
