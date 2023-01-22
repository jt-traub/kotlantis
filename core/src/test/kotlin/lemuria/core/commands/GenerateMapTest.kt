package lemuria.core.commands

import lemuria.core.CommandLineException
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class GenerateMapTest {

    private val testClass: GenerateMap = GenerateMap()

    @Test
    fun testMapTypes() {
        GenerateMap.mapTypes.forEach {
            assertDoesNotThrow({ testClass.checkArgs(it, "dummy") })
        }

        assertFailsWith(CommandLineException::class, { testClass.checkArgs("invalid", "dummy") })
    }
}
