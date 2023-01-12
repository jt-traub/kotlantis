package lemuria.core.commands

import lemuria.core.CommandLineExecutor
import lemuria.core.EditCancelledException
import lemuria.core.GameInterface

class EditGame : CommandLineExecutor {
    override val key = "edit"
    override val usage = "edit"
    override val description = "Modify data in the game file"
    override val argCount = 0

    override fun invoke(game: GameInterface, vararg args: String) {
        checkArgs(*args)
        try {
            game.loadGame()
                .editGame()
                .saveGame()
        } catch (e: EditCancelledException) {
            // This is a deliberate exit from the editor without save or modification
            // We handle it by eating it and just return.  Any other exceptions will be
            // uncaught here and handled in the main function
        }
    }
}
