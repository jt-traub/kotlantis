package lemuria.core.commands

import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import lemuria.core.EditCancelledException
import lemuria.core.GameInterface
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

internal class EditGameTest {

    private val testClass: EditGame = EditGame()

    @Test
    fun testCancellationDoesNotError() {
        val game = mockkClass(GameInterface::class)
        every { game.loadGame() } returns game
        every { game.editGame() } throws EditCancelledException()
        every { game.saveGame() } returns game

        // If the editor is cancelled we should not throw, but we should
        // not call the save either.
        assertDoesNotThrow({ testClass.invoke(game) })
        verify(exactly = 1) { game.loadGame() }
        verify(exactly = 1) { game.editGame() }
        verify(exactly = 0) { game.saveGame() }
    }
}
