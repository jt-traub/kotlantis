package lemuria.core

import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class CommandLineExecutorTest {

    private class DummyExecutor : CommandLineExecutor {
        override val key = "dummy"
        override val usage = "dummy"
        override val description = "Dummy"
        override val argCount = 1

        override fun invoke(game: GameInterface, vararg args: String) { return }
    }

    private val testClass = DummyExecutor()

    @Test
    fun testArgCount() {
        assertFailsWith(CommandLineException::class, { testClass.checkArgs() })
        assertDoesNotThrow({ testClass.checkArgs("one") })
        assertFailsWith(CommandLineException::class, { testClass.checkArgs("one", "two") })
    }
}
