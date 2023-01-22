

package lemuria.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class GameConfigTest {
    @Test
    fun createWorld() {
        val conf = """
        {
            "ruleset": "test",
            "version": "0.0.0",
            "world": "MyTest",
            "flags": [],
            "setup": [
                { "name": "layer1", "type": "type1" },
                { "name": "layer2", "type": "type2" },
                { "name": "layer3", "type": "type3" }
            ]
        }
        """

        val gamedef = GameConfig(JsonStringReader(conf))
        assertNull(gamedef.map)
        gamedef.createWorld()
        assertNotNull(gamedef.map)
        assertEquals(3, gamedef.map!!.layers.size)
        assertEquals("layer2", gamedef.map!!.layers[1]?.name)
    }
}
